package com.example.studen_android.screens.base

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.studen_android.screens.load.LoadScreen
import com.example.studen_android.screens.questions.QuestionsScreen
import com.example.studen_android.screens.update.UpdateScreen
import com.example.studen_android.screens.webview.WebViewScreen

@Composable
fun BaseScreen(
    viewModel: BaseVM,
    navController: NavController
) {

    val viewState by viewModel.viewState.collectAsState()

    when (viewState) {
        BaseViewState.Load -> LoadScreen(navController)
        BaseViewState.WebView -> WebViewScreen()
        BaseViewState.Native -> QuestionsScreen()
        BaseViewState.Update -> WebViewScreen()
    }

}