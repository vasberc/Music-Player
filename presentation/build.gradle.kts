plugins {
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    alias(libs.plugins.ksp)
    alias(libs.plugins.jetbrainsKotlinSerialization)
    alias(libs.plugins.composeCompiler)
}

android {
    namespace = "com.vasberc.presentation"
    compileSdk = 35

    defaultConfig {
        minSdk = 26
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
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
    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

ksp {
    arg("KOIN_CONFIG_CHECK", "true")
}

dependencies {
    implementation ("androidx.compose.material:material-icons-extended:1.6.0")
    implementation("androidx.media3:media3-ui-compose:1.6.0")
    implementation ("androidx.media3:media3-exoplayer:1.6.0")
    implementation ("androidx.media3:media3-common:1.6.0")
    implementation ("androidx.media3:media3-session:1.6.0")
    implementation(project(":domain"))
    implementation(platform(libs.androidx.compose.bom))
    ksp(libs.koinKsp)
    implementation(libs.bundles.core)
    implementation(libs.bundles.presentation)
    testImplementation(libs.bundles.testing)
    androidTestImplementation(libs.bundles.androidTesting)
    debugImplementation(libs.bundles.presentationDebug)
}