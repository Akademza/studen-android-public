package ai.pifagor.android.custom_composables

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ai.pifagor.android.ui.theme.MainOrange

@Composable
fun MainOrangeButton(
    modifier: Modifier = Modifier,
    text: String,
    enabled: Boolean = true,
    isLoading: Boolean = false,
    onClick: () -> Unit,
) {
    Button(
        enabled = enabled,
        onClick = { onClick() },
        shape = RoundedCornerShape(8.dp),
        colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent, contentColor = Color.White),
        contentPadding = PaddingValues(0.dp, 0.dp),
        modifier = modifier
//            .fillMaxWidth()
            .height(56.dp)
    ) {
        val background = if (enabled) Modifier.background(MainOrange) else Modifier.background(Color.Gray)
        Box(
            modifier = background.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            if (isLoading) {
                CircularProgressIndicator(color = MainOrange)
            } else {
                Row() {
                    Text(
                        text = text,
                        color = Color.White,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }
    }
}


@Composable
@Preview(showBackground = false)
fun MainOrangeButtonPreview() {
    MainOrangeButton(text = "Login", enabled = true, isLoading = false) {}
}