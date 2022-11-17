package com.example.imchic

import android.app.Application
import com.example.imchic.base.handler.ExceptionHandler
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class DemoApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        setCrashHandler()
    }

    private fun setCrashHandler() {
        val crashlyticsExceptionHandler = Thread.getDefaultUncaughtExceptionHandler() ?: return
        Thread.setDefaultUncaughtExceptionHandler(
            ExceptionHandler(
                this,
                crashlyticsExceptionHandler
            )
        )
    }


}