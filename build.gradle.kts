buildscript {

    val jacocoVersion by extra { "0.8.7" }

    repositories {
        google()
        mavenCentral()
    }

    dependencies {
        classpath(ClasspathDependencies.androidGradlePlugin)
        classpath(ClasspathDependencies.kotlinGradlePlugin)
        classpath(ClasspathDependencies.navComponentSafeVarargs)
        classpath("org.jacoco:org.jacoco.core:$jacocoVersion")
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}