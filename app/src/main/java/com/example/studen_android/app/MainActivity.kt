package com.example.studen_android.app

import android.os.Bundle
import android.util.Log
import android.webkit.CookieManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.rememberNavController
import com.apphud.sdk.Apphud
import com.example.studen_android.BuildConfig
import com.example.studen_android.screens.base.BaseScreen
import com.example.studen_android.screens.base.BaseVM
import com.example.studen_android.ui.theme.StudenAndroidTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            StudenAndroidTheme {

                val navController = rememberNavController()
                val context = LocalContext.current

                Apphud.start(context, BuildConfig.APPHUD_KEY)

                BaseScreen(navController = navController, viewModel = BaseVM)

            }
        }
    }
}