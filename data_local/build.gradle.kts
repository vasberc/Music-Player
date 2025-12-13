plugins {
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    alias(libs.plugins.ksp)
}

kotlin {
    // Use Kotlin JVM toolchain to set the target JVM version (replaces deprecated kotlinOptions.jvmTarget)
    jvmToolchain(21)
}

android {
    namespace = "com.vasberc.data_local"
    compileSdk = 36

    defaultConfig {
        minSdk = 24
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }
}

//ksp {
//    arg("KOIN_CONFIG_CHECK", "true")
//}

dependencies {
    implementation(project(":domain"))
    implementation(platform(libs.koin.bom))
    ksp(libs.koinKsp)
    ksp(libs.roomKsp)
    implementation(libs.bundles.core)
    implementation(libs.bundles.dataLocal)
    testImplementation(libs.bundles.testing)
    androidTestImplementation(libs.bundles.androidTesting)
}