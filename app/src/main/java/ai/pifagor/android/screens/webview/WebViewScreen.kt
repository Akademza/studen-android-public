package ai.pifagor.android.screens.webview

import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import android.webkit.ValueCallback
import android.webkit.WebView
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import ai.pifagor.android.BuildConfig
import ai.pifagor.android.app.MainActivity
import ai.pifagor.android.data.repositories.MainRepository
import android.widget.Toast
import com.google.accompanist.web.*

@Composable
fun WebViewScreen() {

    val context = LocalContext.current
    var url by remember { mutableStateOf(BuildConfig.WEB_VIEW_URL) }
    val state = rememberWebViewState(url = url)
    val navigator = rememberWebViewNavigator()
    val webClient = remember {
        object : AccompanistWebViewClient() {
            override fun onPageStarted(
                view: WebView?,
                url: String?,
                favicon: Bitmap?
            ) {
                super.onPageStarted(view, url, favicon)
                Log.d("Accompanist WebView", "$")
            }

            override fun onLoadResource(view: WebView?, url: String?) {
                super.onLoadResource(view, url)
                Log.d("Accompanist WebView", "$")
            }

            override fun onPageCommitVisible(view: WebView?, url: String?) {
                super.onPageCommitVisible(view, url)
                Log.d("Accompanist WebView", "$")
            }
        }
    }

    val launcher = rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) { uri: Uri? ->
    }

    val webChrome = remember {
        object : AccompanistWebChromeClient() {
            override fun onShowFileChooser(
                webView: WebView?,
                filePathCallback: ValueCallback<Array<Uri>>?,
                fileChooserParams: FileChooserParams?
            ): Boolean {
                val activity = context as MainActivity
                activity.photoLoader.openFileChooser(filePathCallback)

                //launcher.launch("image/*")
                //return super.onShowFileChooser(webView, filePathCallback, fileChooserParams)
                return true
            }
        }
    }


    WebView(
        state = state,
        modifier = Modifier.fillMaxSize(),
        navigator = navigator,
        onCreated = { webView ->
            webView.settings.allowFileAccess = true
            webView.settings.allowContentAccess = true
            webView.settings.allowFileAccessFromFileURLs = true
            webView.settings.allowUniversalAccessFromFileURLs = true
            webView.settings.domStorageEnabled = true
            webView.settings.databaseEnabled = true
            webView.settings.javaScriptCanOpenWindowsAutomatically = true
            webView.settings.javaScriptEnabled = true
            webView.settings.userAgentString = "Mozilla/5.0 (Linux; Android 10; HD1913) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.6099.210 Mobile Safari/537.36 EdgA/120.0.2210.126"
            webView.loadUrl(BuildConfig.WEB_VIEW_URL, mapOf("Authorization" to "Bearer ${MainRepository.getAuthToken()}"))
        },
        client = webClient,
        captureBackPresses = false,
        chromeClient = webChrome
    )
}