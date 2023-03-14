buildscript {

    repositories {
        google()
        mavenCentral()
        maven {
            setUrl("https://plugins.gradle.org/m2/")
        }
    }

    dependencies {
        classpath(ClasspathDependencies.androidGradlePlugin)
        classpath(ClasspathDependencies.kotlinGradlePlugin)
        classpath(ClasspathDependencies.navComponentSafeVarargs)
        classpath(ClasspathDependencies.hiltPlugin)
        classpath(ClasspathDependencies.jacocoPlugin)
        classpath("org.jlleitschuh.gradle:ktlint-gradle:9.2.1")
    }
}

allprojects {
    apply {
        plugin("org.jlleitschuh.gradle.ktlint")
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}
