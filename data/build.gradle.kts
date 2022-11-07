plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

android {
    compileSdk = 32

    defaultConfig {
        applicationId = AppConfig.applicationId
        minSdk = AppConfig.minSdk
        targetSdk = AppConfig.targetSdk
        versionCode = AppConfig.versionCode
        versionName = AppConfig.versionName

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = AppConfig.javaVersion
    }
}

dependencies {

    // project implementation
    //implementation(project(":app"))

    implementation(Libraries.KTX.CORE)

    implementation(Libraries.Google.MATERIAL)

    implementation(Libraries.AndroidX.APP_COMPAT)
    implementation(Libraries.AndroidX.CONSTRAINT_LAYOUT)

    implementation(Libraries.Test.JUNIT)
    implementation(Libraries.AndroidTest.ESPRESSO_CORE)
}