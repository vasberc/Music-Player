plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    alias(libs.plugins.ksp)
}

kotlin {
    // Use Kotlin JVM toolchain to set the target JVM version (replaces deprecated kotlinOptions.jvmTarget)
    jvmToolchain(21)
}

android {
    namespace = "com.vasberc.musicplayer"
    compileSdk = 36

    //For KSP to access generated code
    applicationVariants.configureEach {
        kotlin.sourceSets {
            getByName(name) {
                kotlin.srcDir("build/generated/ksp/${name}/kotlin")
            }
        }
    }

    defaultConfig {
        applicationId = "com.vasberc.musicplayer"
        minSdk = 26
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

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
    implementation(project(":presentation"))
    implementation(project(":domain"))
    implementation(project(":data_local"))
    implementation(platform(libs.koin.bom))
    ksp(libs.koinKsp)
    implementation(libs.bundles.core)
    implementation(libs.bundles.app)
    testImplementation(libs.bundles.testing)
    androidTestImplementation(libs.bundles.androidTesting)
}