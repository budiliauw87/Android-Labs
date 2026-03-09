package dev.liau.kmppractice.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.LifecycleResumeEffect
import androidx.lifecycle.compose.LocalLifecycleOwner
import dev.liau.kmppractice.AppLogger
import kmppractice.composeapp.generated.resources.Res
import kmppractice.composeapp.generated.resources.app_name
import kmppractice.composeapp.generated.resources.splash_icon
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun ScreenSplash(modifier: Modifier) {
    val scope = rememberCoroutineScope()
    val lifecycleOwner = LocalLifecycleOwner.current
    val stateFlow = lifecycleOwner.lifecycle.currentStateFlow
    val currentLifecycleState by stateFlow.collectAsState()


    /**
     * Lifecycle Resume Effect
     *
     */
    LifecycleResumeEffect(Unit) {
        // ON_RESUME code is executed here
        AppLogger.i("LifecycleEventEffect", "ON_RESUME")
        onPauseOrDispose {
            // do any needed clean up here
            AppLogger.i("LifecycleEventEffect", "ON_PAUSE")
        }
    }

    /**
     * Initializing Application here
     */
    LaunchedEffect(Unit){
        scope.launch {
            println("Demo Splash Screen")
            delay(2000)
            println("Init Other request in Splash Screen")
        }

    }

    Column(modifier = Modifier.fillMaxSize()
        .background(color = MaterialTheme.colorScheme.primary )
        .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Bottom
    ) {
        // Other UI elements (placed on top of the image)
        // You can add Columns, Rows, Text, etc., here.
        Text(
            text = stringResource(Res.string.app_name),
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.background
        )
        // Logo Image
        Image(
            painter = painterResource(Res.drawable.splash_icon), // Replace with your drawable name
            contentDescription = "Background Image", // Accessibility description
            contentScale = ContentScale.FillBounds, // Scales the image to fill the bounds
            modifier = Modifier.size(width = 150.dp, height = 150.dp)
        )

    }
}