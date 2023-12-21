package ai.pifagor.android.navigation

sealed class Screens(val route: String) {
    object Load: Screens(route = "load")
    object WebView: Screens(route = "web_view")
}
