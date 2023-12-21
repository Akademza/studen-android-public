package ai.pifagor.android.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

const val ROOT_NAV_GRAPH = "ROOT_NAV_GRAPH"

@Composable
fun SetupNavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    startDestinationGraph: String
) {
    NavHost(
        navController = navController,
        startDestination = startDestinationGraph,
        modifier = modifier,
        route = ROOT_NAV_GRAPH
    ) {

        composable(route = Screens.Load.route) {
        }
    }
}