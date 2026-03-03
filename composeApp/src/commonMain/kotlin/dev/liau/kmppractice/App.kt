package dev.liau.kmppractice

import SnackbarManager
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.compose.AppTheme
import dev.liau.kmppractice.ui.navigation.NavigationApp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun App(
    isDarkTheme: Boolean = false,
) {
    AppTheme(
        isDarkTheme
    ) {
        NavigationApp()
    }
}


@Preview(
    showSystemUi = true,
    showBackground = true,
)
@Composable
fun defaultPreview() {
    App(false)
}

@Preview(
    name = "Dark Mode",
    showSystemUi = true,
    showBackground = true,
)
@Composable
fun previewDark() {
    App(true)
}

