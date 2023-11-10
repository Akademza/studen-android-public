package com.example.studen_android.screens.update

import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.studen_android.R
import com.example.studen_android.custom_composables.MainOrangeButton
import android.net.Uri
import androidx.compose.ui.platform.LocalContext

@Composable
fun UpdateScreen() {
    val context = LocalContext.current
    val googlePlay = remember { Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps")) }

    Column(
        modifier = Modifier.fillMaxSize().padding(30.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = stringResource(id = R.string.app_name),
            fontSize = 36.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Gray,
            modifier = Modifier.padding(bottom = 30.dp)
        )

        Image(
            painter = painterResource(id = R.drawable.update_icon),
            contentDescription = null,
            modifier = Modifier.padding(bottom = 20.dp)
        )

        Text(
            text = stringResource(id = R.string.more_features_in_new_version),
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold,
            color = Color.DarkGray,
            modifier = Modifier.padding(bottom = 65.dp)
        )
        
        MainOrangeButton(
            text = stringResource(id = R.string.update_now),
            onClick = { context.startActivity(googlePlay)}
        )
    }
}