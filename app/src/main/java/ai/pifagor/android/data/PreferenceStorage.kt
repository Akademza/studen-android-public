package ai.pifagor.android.data

import android.content.Context
import android.content.SharedPreferences
import ai.pifagor.android.app.App

object PreferenceStorage {

    private val context = App.INSTANCE.appContext
    val authPrefs: SharedPreferences = context.getSharedPreferences("authFile", Context.MODE_PRIVATE)

}