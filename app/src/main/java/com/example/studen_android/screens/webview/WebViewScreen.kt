package com.example.studen_android.screens.webview

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
import com.example.studen_android.BuildConfig
import com.example.studen_android.data.repositories.MainRepository
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
                launcher.launch("image/*")
                return super.onShowFileChooser(webView, filePathCallback, fileChooserParams)
            }
        }
    }


    WebView(
        state = state,
        modifier = Modifier.fillMaxSize(),
        navigator = navigator,
        onCreated = { webView ->
            webView.settings.javaScriptEnabled = true
            webView.settings.allowFileAccess = true
            webView.settings.allowContentAccess = true
            webView.settings.userAgentString = "Mozilla/5.0 (iPhone; CPU iPhone OS 13_2_3 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/13.0.3 Mobile/15E148 Safari/604.1"

            webView.loadUrl(BuildConfig.WEB_VIEW_URL, mapOf("Authorization" to "Bearer ${MainRepository.getAuthToken()}"))
        },
        client = webClient,
        captureBackPresses = false,
        chromeClient = webChrome
    )
}