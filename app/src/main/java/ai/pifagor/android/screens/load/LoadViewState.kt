package ai.pifagor.android.screens.load

sealed class LoadViewState {
    object Loading: LoadViewState()
    object Success: LoadViewState()
    data class Error(val message: String): LoadViewState()
}
