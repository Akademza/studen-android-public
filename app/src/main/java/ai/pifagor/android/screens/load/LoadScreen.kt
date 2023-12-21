package ai.pifagor.android.screens.load

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import ai.pifagor.android.R
import ai.pifagor.android.custom_composables.BrandError
import ai.pifagor.android.custom_composables.BrandLoader
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@Composable
fun LoadScreen(
    navController: NavController,
    viewModel: LoadViewModel = viewModel()
) {
    val context = LocalContext.current
    val viewState by viewModel.viewState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.messages.onEach {
            Toast.makeText(context, it, Toast.LENGTH_LONG).show()
        }.launchIn(this)
    }

    when (val state = viewState) {
        LoadViewState.Loading -> BrandLoader()
        is LoadViewState.Error -> BrandError(state.message) {
            viewModel.auth()
        }
        LoadViewState.Success -> BrandLoader()
    }

}