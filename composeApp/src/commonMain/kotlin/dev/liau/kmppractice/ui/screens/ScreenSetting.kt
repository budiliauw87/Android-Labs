package dev.liau.kmppractice.ui.screens

import CustomSnackbarVisuals
import SnackbarManager
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import kotlinx.coroutines.launch

@Composable
@Preview(
    showSystemUi = true,
    showBackground = true
)
fun ScreenSetting(modifier: Modifier) {
    val scope = rememberCoroutineScope()
    Box(
        modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text("Show Snackbar")
        Button(onClick = {
            scope.launch {
              val result=  SnackbarManager.showSnackBar(
                    CustomSnackbarVisuals(
                        "Snackbar from setting screen",
                        null,
                        false,
                        SnackbarDuration.Short
                    )
                )


            }

        }){
            Text("show snackbar!")
        }
    }
}

