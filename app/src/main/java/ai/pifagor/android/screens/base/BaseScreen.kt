package ai.pifagor.android.screens.base

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import androidx.lifecycle.viewmodel.compose.viewModel
import ai.pifagor.android.screens.load.LoadScreen
import ai.pifagor.android.screens.questions.QuestionsScreen
import ai.pifagor.android.screens.update.UpdateScreen
import ai.pifagor.android.screens.webview.WebViewScreen

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
        else -> WebViewScreen()
    }

}