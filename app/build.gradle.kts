plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("android.extensions")
    kotlin("kapt")
    id("androidx.navigation.safeargs.kotlin")
    id("dagger.hilt.android.plugin")
}
apply(from = "buildTypes.gradle")
apply(from = "jacoco.gradle")

kapt {
    correctErrorTypes = true
}

android {
    compileSdk = VersionApp.compileSdkVersion

    defaultConfig {
        applicationId = "com.amarturelo.usersgithub"
        minSdk = VersionApp.minSdkVersion
        targetSdk = VersionApp.targetSdkVersion
        versionCode = 1
        versionName = ConfigData.versionName
        vectorDrawables.useSupportLibrary = true
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    buildFeatures {
        dataBinding = true
        viewBinding = true
    }

    testOptions {
        unitTests.isReturnDefaultValues = true
    }

    kotlinOptions {
        jvmTarget = "1.8"
        kotlinOptions.freeCompilerArgs += "-Xjvm-default=enable"
    }

    packagingOptions {
        resources.excludes.add("META-INF/proguard/androidx-annotations.pro")
        resources.excludes.add("META-INF/androidx.exifinterface_exifinterface.version")
        resources.excludes.add("META-INF/*.kotlin_module")
        resources.pickFirsts.add("lib/armeabi-v7a/libtool-checker.so")
        resources.pickFirsts.add("lib/arm64-v8a/libtool-checker.so")
        resources.pickFirsts.add("lib/x86/libtool-checker.so")
        resources.pickFirsts.add("lib/x86_64/libtool-checker.so")
        pickFirst("lib/armeabi-v7a/libRSSupport.so")
        pickFirst("lib/arm64-v8a/libRSSupport.so")
        pickFirst("lib/x86_64/libRSSupport.so")
        pickFirst("lib/x86/libRSSupport.so")
        pickFirst("lib/arm64-v8a/librsjni.so")
        pickFirst("lib/x86/librsjni.so")
        pickFirst("lib/x86_64/librsjni.so")
        pickFirst("lib/armeabi-v7a/librsjni.so")
        pickFirst("lib/x86_64/librsjni_androidx.so")
        pickFirst("lib/armeabi-v7a/librsjni_androidx.so")
        pickFirst("lib/x86/librsjni_androidx.so")
        pickFirst("lib/arm64-v8a/librsjni_androidx.so")
    }
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar", "*.aar"))))

    /* Google-Android libraries */
    implementation(ApplicationDependencies.xAppCompat)
    implementation(ApplicationDependencies.xDesign)
    implementation(ApplicationDependencies.xConstraintLayout)

    implementation(ApplicationDependencies.gson)
    implementation(ApplicationDependencies.okhttp)
    implementation(ApplicationDependencies.retrofit)
    implementation(ApplicationDependencies.retrofitGson)
    implementation(ApplicationDependencies.okhttpLoggingInterceptor)

    implementation(ApplicationDependencies.timber)

    // hilt
    implementation(ApplicationDependencies.hiltAndroid)
    kapt(ApplicationDependencies.hiltAndroidCompiler)
    kapt(ApplicationDependencies.xHiltCompiler)

    // test
    testImplementation(UnitTestingDependencies.junit)
    testImplementation(UnitTestingDependencies.mockito)
    testImplementation(UnitTestingDependencies.mockWebServer)
    testImplementation(UnitTestingDependencies.xLifecycleViewmodel)
    testImplementation(UnitTestingDependencies.coroutinesTestKt)
    testImplementation(UnitTestingDependencies.xCoreTesting)
    testImplementation(UnitTestingDependencies.mockito)
    androidTestImplementation(UnitTestingDependencies.testRunner)
    testImplementation(UnitTestingDependencies.mockk)
    implementation(UnitTestingDependencies.junit)

    implementation(ApplicationDependencies.xLifecycleViewmodel)
    implementation(ApplicationDependencies.lifecycleRuntime)
    implementation(ApplicationDependencies.lifecycleLivedata)

    implementation(ApplicationDependencies.kotlinStdLib)

    implementation(ApplicationDependencies.xNavComponentCommonKtx)
    implementation(ApplicationDependencies.xNavComponentFragmentKtx)
    implementation(ApplicationDependencies.xNavComponentUiKtx)

    implementation(ApplicationDependencies.epoxy)
    kapt(ApplicationDependencies.epoxyProcessor)


    implementation("androidx.swiperefreshlayout:swiperefreshlayout:1.1.0")

    // Coroutines
    implementation(ApplicationDependencies.coroutinesCore)
    implementation(ApplicationDependencies.coroutinesAndroid)

    //Logger
    implementation(ApplicationDependencies.timber)

    //Glide
    implementation(ApplicationDependencies.glide)
    kapt(ApplicationDependencies.glideCompiler)

    //Modules
    implementation(ModulesDependencies.ugCore)
    implementation(ModulesDependencies.ugFollowers)

    //LocalModules.setupBuildGradle(this, rootProject)
}