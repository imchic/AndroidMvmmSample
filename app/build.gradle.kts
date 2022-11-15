import java.util.Properties

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
}

android {
    compileSdk = 32


    // local.properties 가져오기
    val localProperties = Properties().apply {
        load(rootProject.file("local.properties").inputStream())
    }

    defaultConfig {
        applicationId = AppConfig.applicationId
        minSdk = AppConfig.minSdk
        targetSdk = AppConfig.targetSdk
        versionCode = AppConfig.versionCode
        versionName = AppConfig.versionName

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        ndk {
            abiFilters.add("armeabi-v7a")
            abiFilters.add("arm64-v8a")
        }
    }

    signingConfigs {
        create("release") {
            keyAlias = "key0"
            keyPassword = "qwe123"
            storePassword = "qwe123"
            storeFile = file("../keystore/imchic.jks")
        }
    }

    buildTypes {
        getByName("debug") {
            isMinifyEnabled = false
            isDebuggable = true
            isTestCoverageEnabled = true
            applicationIdSuffix = ".debug"
            buildConfigField("String", "api_url", localProperties.getProperty("api_url"))
            buildConfigField("String", "BASE_URL", "\"${AppConfig.debugUrl}\"")
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
        getByName("release") {
            signingConfig = signingConfigs.getByName("release")
            isMinifyEnabled = true
            isDebuggable = false
            applicationIdSuffix = ".release"
            buildConfigField("String", "BASE_URL", "\"${AppConfig.releaseUrl}\"")
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
    buildFeatures {
        dataBinding = true
    }
    lint {
        abortOnError = false
    }
    ndkVersion = "25.1.8937393"
    buildToolsVersion = "30.0.2"
}

dependencies {

    // aar library add
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar", "*.aar"))))

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