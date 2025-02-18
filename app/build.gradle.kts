plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.example.exacuity"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.exacuity"
        minSdk = 29
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

    }

    configurations.all {
        resolutionStrategy.eachDependency {
            if (requested.group == "org.jetbrains.kotlin") {
                useVersion("1.8.22")
            }
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {
    implementation(libs.androidx.leanback)
    implementation(libs.glide)
    implementation(libs.androidx.appcompat.v161)
    implementation(libs.androidx.cardview)
}