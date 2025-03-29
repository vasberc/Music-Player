plugins {
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    alias(libs.plugins.ksp)
}
android {
    namespace = "com.vasberc.domain"
    compileSdk = 35

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
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_21.toString()
    }
}
//
//ksp {
//    arg("KOIN_CONFIG_CHECK", "true")
//}

dependencies {
    implementation ("androidx.media3:media3-exoplayer:1.6.0")
    implementation ("androidx.media3:media3-ui:1.6.0")
    implementation ("androidx.media3:media3-common:1.6.0")
    implementation ("androidx.media3:media3-session:1.6.0")
    ksp(libs.koinKsp)
    implementation(libs.bundles.core)
    testImplementation(libs.bundles.testing)
}

