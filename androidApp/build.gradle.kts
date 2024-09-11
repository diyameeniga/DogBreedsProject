// File: androidApp/build.gradle.kts

plugins {
    id("com.android.application")
    kotlin("android")
}

android {
    namespace = "com.example.androidApp" // Add your unique namespace here
    compileSdk = 33

    defaultConfig {
        applicationId = "com.example.androidApp"
        minSdk = 21
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
}

dependencies {
    implementation(project(":shared"))
    implementation("org.jetbrains.kotlin:kotlin-stdlib:1.9.0")
    implementation("androidx.compose.ui:ui:1.0.5")
    implementation("androidx.compose.material:material:1.0.5")
    implementation("androidx.compose.ui:ui-tooling:1.0.5")
    implementation("androidx.activity:activity-compose:1.3.1")
}
