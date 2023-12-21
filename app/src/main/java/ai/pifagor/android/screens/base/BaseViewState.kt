package ai.pifagor.android.screens.base

sealed class BaseViewState {
    object Load: BaseViewState()
    object WebView: BaseViewState()
    object Native: BaseViewState()
    object Update: BaseViewState()
    data class Error(val message: String): BaseViewState()
}
