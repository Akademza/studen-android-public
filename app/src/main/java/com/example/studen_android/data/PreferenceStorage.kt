package com.example.studen_android.data

import android.content.Context
import android.content.SharedPreferences
import com.example.studen_android.app.App

object PreferenceStorage {

    private val context = App.INSTANCE.appContext
    val authPrefs: SharedPreferences = context.getSharedPreferences("authFile", Context.MODE_PRIVATE)

}