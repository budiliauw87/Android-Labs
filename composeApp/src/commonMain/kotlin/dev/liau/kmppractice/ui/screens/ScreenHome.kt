package dev.liau.kmppractice.ui.screens

import CustomSnackbarVisuals
import SnackbarManager
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch


@Composable
fun ScreenHome(modifier: Modifier) {
    // Creates and remembers the scroll state across recompositions
    val scrollState = rememberScrollState()
    val scope = rememberCoroutineScope()

    Column(
        modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(scrollState),
    ) {

        Text("Showing Snackbar from child composable")

        Button(onClick = {
            scope.launch {
                val snackbar = SnackbarManager.showSnackBar(
                    CustomSnackbarVisuals(
                        message = "This is example message snackbar with two line of text",
                        actionLabel = null,
                        withDismissAction = true,
                        duration = SnackbarDuration.Short
                    )
                )
                when (snackbar){
                    SnackbarResult.ActionPerformed -> {
                        /** handle action performed */
                        println("ActionPerformed Dismissed")
                    }
                    SnackbarResult.Dismissed -> {
                        // Code to execute when the snackbar is dismissed without action
                        println("Snackbar Dismissed working")
                    }
                }

            }
        }) {
            Text("show snackbar!")
        }
        HorizontalDivider(
            modifier = Modifier.padding(vertical = 8.dp)
        )
        Text("Navigation 3",
            style = MaterialTheme.typography.titleLarge)
        Text("Example usage Compose Navigation3")

        FlowRow(
            modifier = Modifier.padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)

        ) {
            Button(
                onClick = {}
            ){
                Text("Basic")
            }
            Button(
                onClick = {}
            ){
                Text("Basic")
            }
        }


    }
}

@Preview(
    name="homescreen",
    showBackground = true,
)
@Composable
fun ScreenHomePreview() {
    // Provide sample data for the preview
    ScreenHome(modifier = Modifier)
}