pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven {
            url = uri("https://amarturelo.jfrog.io/artifactory/libs-snapshot-local/")
            credentials {
                username = "amartur"
                password = "Om3g4123*"
            }
        }
    }
}

include(":app")

apply(from = "./buildSrc/localModules.gradle.kts")
