plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
}

android {
    compileSdk = AppConfig.compileSdk

    defaultConfig {
        applicationId = AppConfig.applicationId
        minSdk = AppConfig.minSdk
        targetSdk = AppConfig.targetSdk
        versionCode = AppConfig.versionCode
        versionName = AppConfig.versionName

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        getByName("release") {
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
    kotlinOptions {
        jvmTarget = AppConfig.javaVersion
    }
    buildFeatures {
        dataBinding = true
    }
}

dependencies {

    implementation(Libraries.KTX.CORE)
    implementation(Libraries.KTX.PREF)

    implementation(Libraries.AndroidX.APP_COMPAT)
    implementation(Libraries.AndroidX.MATERIAL)
    implementation(Libraries.AndroidX.CONSTRAINT_LAYOUT)
    implementation(Libraries.AndroidX.NAVIGATION_FRAGMENT_KTX)
    implementation(Libraries.AndroidX.NAVIGATION_UI_KTX)

    implementation(Libraries.OPENSOURCE.LOTTIE)
    implementation(Libraries.OPENSOURCE.CUSTOM_TOAST)

    implementation(Libraries.Test.JUNIT)
    implementation(Libraries.AndroidTest.ESPRESSO_CORE)
}