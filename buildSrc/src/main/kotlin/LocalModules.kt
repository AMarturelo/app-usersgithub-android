import groovy.lang.Closure
import org.gradle.api.Project
import org.gradle.api.artifacts.ModuleDependency
import org.gradle.api.artifacts.ResolutionStrategy
import org.gradle.api.artifacts.dsl.DependencyHandler
import org.gradle.kotlin.dsl.closureOf
import org.gradle.kotlin.dsl.extra

object LocalModules {

    fun setupBuildGradle(dependencyHandler: DependencyHandler, rootProject: Project) {
        val modules = rootProject.extra.get("modules") as Map<String, Boolean>
        val localModules = modules.filterValues { it }.keys
        val excludedClosure = excludedClosure(localModules)

        val isCICD = isCICD()
        println("isPipeline build: $isCICD")

        modules.forEach { (module, isIncluded) ->
            if (isIncluded && !isCICD) {
                val project = dependencyHandler.project(mapOf("path" to ":$module"))
                dependencyHandler.add("implementation", project)
            } else {
                val implementation = getImplementation(module)
                dependencyHandler.add("implementation", implementation, excludedClosure)
            }
        }
    }

    private fun getImplementation(moduleName: String): String = try {
        ModulesDependencies::class.java.getDeclaredField(moduleName).get(this).toString()
    } catch (e: Exception) {
        throw Exception("The field $moduleName not found in ModulesDependencies")
    }

    private fun getImplementationSegments(implementation: String): List<String> {
        val segments = implementation.split(":")
        if (segments.size != 3) {
            throw Exception("Invalid format '$implementation'. The correct format is [groupId]:[artifactId]:[version]")
        }
        return segments
    }

    private fun getGroupId(implementation: String): String {
        return getImplementationSegments(implementation)[0]
    }

    private fun getArtifactId(implementation: String): String {
        return getImplementationSegments(implementation)[1]
    }

    private fun implementationRequireFlavors(implementation: String): Boolean {
        val artifactId = getArtifactId(implementation)
        return (artifactId == "google" || artifactId == "huawei")
    }

    private fun addDependenciesPerFlavors(
        dependency: DependencyHandler,
        googleDependency: String,
        huaweiDependency: String,
        excludedClosure: Closure<Any?>,
    ) {
        println("implementation huawei: $huaweiDependency")
        println("implementation google: $googleDependency")
        dependency.add("huaweiImplementation", huaweiDependency, excludedClosure)
        dependency.add("googleImplementation", googleDependency, excludedClosure)
    }

    private fun excludedClosure(localModules: Set<String>): Closure<Any?> {
        return closureOf<ModuleDependency> {
            localModules.forEach { module ->
                val implementation = getImplementation(module)
                val excludedProperties = mapOf(
                    "group" to getGroupId(implementation),
                    "module" to getArtifactId(implementation),
                )
                exclude(excludedProperties)
            }
        }
    }

    fun forceLocals(
        resolutionStrategy: ResolutionStrategy,
        rootProject: Project,
        forcedDependencies: Array<String>
    ) {
        resolutionStrategy.force(forcedDependencies)

        if (isCICD()) return

        val modules = rootProject.extra.get("modules") as Map<String, Boolean>
        modules.forEach { (moduleName, isIncluded) ->

            if (isIncluded && rootProject.findProject(":$moduleName") != null) {
                try {
                    val declaredField = ModulesDependencies::class
                        .java
                        .declaredFields
                        .firstOrNull { it.name == moduleName }
                        ?.get(null)
                        ?.toString()

                    declaredField?.let {
                        if (declaredField in forcedDependencies) {
                            resolutionStrategy.dependencySubstitution {
                                val dependencyArray = it.split(":")
                                val module = module("${dependencyArray[0]}:${dependencyArray[1]}")

                                substitute(module).using(project(":$moduleName"))
                            }
                        }
                    }
                } catch (e: Exception) {
                    println("Error in local module $moduleName substitution")
                    println(e)
                }
            }
        }
    }

    fun isCICD() = System.getenv("IS_CICD")?.isNotEmpty() ?: false

}
