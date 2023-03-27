dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        jcenter()
        maven {
            name = "GitHub Core"
            url = uri("https://maven.pkg.github.com/amarturelo/usersgithub-android-core")
            credentials {
                username = System.getenv("GITHUB_USER")
                password = System.getenv("GITHUB_ACCESS_TOKEN")
            }
        }

        maven {
            name = "GitHub Followers"
            url = uri("https://maven.pkg.github.com/amarturelo/usersgithub-android-followers")
            credentials {
                username = System.getenv("GITHUB_USER")
                password = System.getenv("GITHUB_ACCESS_TOKEN")
            }
        }
    }
}

include(":app")
