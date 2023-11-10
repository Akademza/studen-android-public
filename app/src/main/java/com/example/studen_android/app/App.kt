package com.example.studen_android.app

import android.app.Application
import android.content.Context

class App: Application() {

    lateinit var appContext: Context

    companion object {
        lateinit var INSTANCE: App
            private set
    }

    override fun onCreate() {
        super.onCreate()
        INSTANCE = this
        appContext = applicationContext
    }
}