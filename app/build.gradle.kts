plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
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

    implementation(Libraries.Google.MATERIAL)
    implementation(Libraries.Google.HILT)
    kapt(Libraries.Google.HILT_COMPILER)

    implementation(Libraries.AndroidX.APP_COMPAT)
    implementation(Libraries.AndroidX.CONSTRAINT_LAYOUT)
    implementation(Libraries.AndroidX.NAVIGATION_FRAGMENT_KTX)
    implementation(Libraries.AndroidX.NAVIGATION_UI_KTX)

    implementation(Libraries.OPENSOURCE.OKHTTP)
    implementation(Libraries.OPENSOURCE.LOTTIE)
    implementation(Libraries.OPENSOURCE.CUSTOM_TOAST)

    implementation(Libraries.Test.JUNIT)
    implementation(Libraries.AndroidTest.ESPRESSO_CORE)
}

kapt {
    correctErrorTypes = true
}