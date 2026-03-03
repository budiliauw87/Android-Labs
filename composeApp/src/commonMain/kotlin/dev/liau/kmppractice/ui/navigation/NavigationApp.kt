package dev.liau.kmppractice.ui.navigation

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.ui.NavDisplay
import com.example.compose.onSecondaryContainerLight

private data object RouteA

private data class RouteB(val id: String)

private data object RouteC

@Composable
fun NavigationApp() {
    val backStack = remember { mutableStateListOf<Any>(RouteA) }

    NavDisplay(
        backStack = backStack,
        onBack = {
            val currentStack = backStack.lastOrNull()
            println("currentStack: $currentStack")
            backStack.removeLastOrNull()
        },
        entryProvider = { key ->
            when (key) {
                is RouteA -> NavEntry(key) {
                    ContentDummy("Welcome to Nav3") {
                        Button(onClick = {
                            backStack.add(RouteB("123"))
                        }) {
                            Text("Click to navigate")
                        }
                    }
                }

                is RouteB -> NavEntry(key) {
                    ContentDummy(
                        title="Route id: ${key.id} ",
                        onNext = {backStack.add(RouteC)}

                    ){
                        Button(onClick = {
                            backStack.removeLastOrNull()
                        }) {
                            Text("Click to go back")
                        }

                    }
                }
                is RouteC -> NavEntry(key) {
                    ContentDummy("Route C"){
                        Button(onClick = {
                            backStack.clear()
                            backStack.add(RouteA)
                        }) {
                            Text("Click to go Home")
                        }
                    }
                }

                else -> {
                    error("Unknown route: $key")
                }
            }
        }
    )
}

@Composable
fun ContentDummy(
    title: String,
    modifier: Modifier = Modifier,
    onNext: (() -> Unit)? = null,
    content: (@Composable () -> Unit)? = null,
) = ContentBase(
    title = title,
    modifier = modifier.background(MaterialTheme.colorScheme.background),
    onNext = onNext,
    content = content
)

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun ContentBase(
    title: String,
    modifier: Modifier = Modifier,
    onNext: (() -> Unit)? = null,
    content: (@Composable () -> Unit)? = null,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxSize()
            .safeDrawingPadding()
            .clip(RoundedCornerShape(48.dp))
    ) {
        Title(title)
        if (content != null) content()
        if (onNext != null) {
            Button(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                onClick = onNext
            ) {
                Text("Next")
            }
        }
    }
}

@Composable
fun ColumnScope.Title(title: String) {
    Text(
        modifier = Modifier
            .padding(24.dp)
            .align(Alignment.CenterHorizontally),
        fontWeight = FontWeight.Bold,
        text = title
    )
}