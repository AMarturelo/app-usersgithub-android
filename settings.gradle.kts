dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven {
            isAllowInsecureProtocol = true
            url = uri("http://localhost:8081/repository/maven-snapshots/")
        }
    }
}

include(":app")

//apply(from = "./buildSrc/localModules.gradle.kts")
