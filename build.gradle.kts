buildscript {

    repositories {
        google()
        mavenCentral()
    }

    dependencies {
        classpath(ClasspathDependencies.androidGradlePlugin)
        classpath(ClasspathDependencies.kotlinGradlePlugin)
        classpath(ClasspathDependencies.navComponentSafeVarargs)
        classpath(ClasspathDependencies.hiltPlugin)
        classpath(ClasspathDependencies.jacocoPlugin)
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}