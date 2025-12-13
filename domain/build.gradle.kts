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
    namespace = "com.vasberc.domain"
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
//
//ksp {
//    arg("KOIN_CONFIG_CHECK", "true")
//}

dependencies {
    ksp(libs.koinKsp)
    implementation(platform(libs.koin.bom))
    implementation(libs.bundles.core)
    testImplementation(libs.bundles.testing)
}

