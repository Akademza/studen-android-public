package ai.pifagor.android.app

import ai.pifagor.android.BuildConfig
import ai.pifagor.android.screens.base.BaseScreen
import ai.pifagor.android.screens.base.BaseVM
import ai.pifagor.android.screens.webview.PhotoLoader
import ai.pifagor.android.ui.theme.StudenAndroidTheme
import android.Manifest
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.webkit.ValueCallback
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.navigation.compose.rememberNavController
import com.apphud.sdk.Apphud
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class MainActivity : ComponentActivity() {
    public lateinit var photoLoader: PhotoLoader
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        photoLoader = PhotoLoader(this)

        setContent {
            StudenAndroidTheme {

                val navController = rememberNavController()
                val context = LocalContext.current

                Apphud.start(context, BuildConfig.APPHUD_KEY)

                BaseScreen(navController = navController, viewModel = BaseVM)

            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(!photoLoader.onActivityResult(requestCode, resultCode, data)) {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }
}