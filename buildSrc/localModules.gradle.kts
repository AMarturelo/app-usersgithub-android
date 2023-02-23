val modules: Map<String, Boolean> = hashMapOf(
    "core" to false,
    "followers" to false,
)

settings.extra.set("modules", modules)

gradle.rootProject {
    this.extra.set("modules", modules)
}

apply {
    plugin(LocalModulesPlugin::class)
}

class LocalModulesPlugin : Plugin<Settings> {

    override fun apply(settings: Settings) {
        if (isCICD()) {
            return
        }
        val modules = settings.extra.get("modules") as Map<String, Boolean>
        if (modules == null) {
            throw kotlin.Exception("Required extension 'modules' is not setted.")
        }
        modules.forEach { (moduleName, isIncluded) ->
            if (isIncluded) {
                val localModule = getLocalModuleByModuleName(moduleName)
                if (localModule == null) {
                    throw kotlin.Exception("Repository Info for module $moduleName not found.")
                }
                cloneRepositoryIfNotExsists(settings, localModule)
                includeLocalModule(settings, localModule)
            }
        }
    }

    private fun isCICD(): Boolean {
        return System.getenv("IS_CICD")?.isNotEmpty() ?: false
    }

    private fun getLocalModuleByModuleName(moduleName: String): LocalModule? {
        return LocalModule.values().find { it.moduleName == moduleName }
    }

    private fun cloneRepositoryIfNotExsists(settings: Settings, localModule: LocalModule) {
        val parentDir = settings.rootProject.projectDir.parent
        val projectDir = getProjectDir(parentDir, localModule)
        if (!projectDir.exists()) {
            cloneRepository(localModule)
        }
    }

    private fun includeLocalModule(settings: Settings, localModule: LocalModule) {
        val parentDir = settings.rootProject.projectDir.parent
        val projectDir = getProjectDir(parentDir, localModule)
        settings.include(localModule.moduleName)
        settings.project(":${localModule.moduleName}").projectDir = projectDir
    }

    private fun getProjectDir(parentDir: String, localModule: LocalModule): java.io.File {
        return File("$parentDir/${localModule.folder}/${localModule.moduleName}")
    }

    private fun cloneRepository(localModule: LocalModule) {
        try {
            val gitCommand = buildGitCommand(localModule)
            val process = Runtime.getRuntime().exec(
                arrayOf("/bin/sh", "-c", gitCommand)
            )
            val stdOut = java.io.BufferedReader(java.io.InputStreamReader(process.inputStream))
            var s: String?
            do {
                s = stdOut.readLine()
            } while (s != null)
        } catch (ex: Exception) {
            println(ex.message)
        }
    }

    private fun buildGitCommand(localModule: LocalModule): String {
        val url = "git@github.com:AMarturelo/${localModule.folder}.git"
        return "git clone --branch ${localModule.branch}  $url ../${localModule.folder} "
    }
}

enum class LocalModule(
    val moduleName: String,
    val folder: String,
    val branch: String = "develop",
) {
    CORE("core", "usersgithub-android-core"),
    FOLLOWERS("followers", "usersgithub-android-followers"),
}