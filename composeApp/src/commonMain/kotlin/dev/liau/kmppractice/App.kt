package dev.liau.kmppractice

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
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
        Scaffold {
            NavigationApp(
                modifier = Modifier.padding(it)
            )
        }
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

