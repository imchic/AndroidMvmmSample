object Versions {

    // AndroidX
    const val APP_COMPAT = "1.5.1"
    const val MATERIAL = "1.8.0-alpha02"
    const val CONSTRAINT_LAYOUT = "2.1.4"
    const val NAVIGATION = "2.5.3"

    // KTX
    const val CORE = "1.7.0"

    // HILT
    const val HILT = "2.44"

    // TEST
    const val JUNIT = "1.1.3"

    // Android Test
    const val ESPRESSO_CORE = "3.4.0"
}

object Libraries {

    object AndroidX {
        const val APP_COMPAT = "androidx.appcompat:appcompat:${Versions.APP_COMPAT}"
        const val DATA_STORE = "androidx.datastore:datastore-preferences:1.0.0"
        const val CONSTRAINT_LAYOUT = "androidx.constraintlayout:constraintlayout:${Versions.CONSTRAINT_LAYOUT}"
        const val NAVIGATION_FRAGMENT_KTX = "androidx.navigation:navigation-fragment-ktx:${Versions.NAVIGATION}"
        const val NAVIGATION_UI_KTX = "androidx.navigation:navigation-ui-ktx:${Versions.NAVIGATION}"
    }

    object Google {
        const val MATERIAL = "com.google.android.material:material:${Versions.MATERIAL}"
        const val HILT = "com.google.dagger:hilt-android:${Versions.HILT}"
        const val HILT_COMPILER = "com.google.dagger:hilt-android-compiler:${Versions.HILT}"
    }

    object KTX {
        const val CORE = "androidx.core:core-ktx:${Versions.CORE}"
        const val PREF = "androidx.preference:preference-ktx:1.2.0"
    }

    object OPENSOURCE {
        const val OKHTTP = "com.squareup.okhttp3:okhttp:4.9.1"
        const val LOTTIE = "com.airbnb.android:lottie:3.0.7"
        const val CUSTOM_TOAST = "com.github.GrenderG:Toasty:1.5.2"
    }

    object Test {
        const val JUNIT = "androidx.test.ext:junit:${Versions.JUNIT}"
        const val ANDROID_JUNIT_RUNNER = "androidx.test.runner.AndroidJUnitRunner"
    }

    object AndroidTest {
        const val ESPRESSO_CORE = "androidx.test.espresso:espresso-core:${Versions.ESPRESSO_CORE}"
    }

}