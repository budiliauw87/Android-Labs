@file:OptIn(ExperimentalComposeUiApi::class)

package dev.liau.kmppractice.ui.navigation

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.backhandler.BackHandler
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.dropUnlessResumed
import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.ui.NavDisplay
import androidx.navigationevent.NavigationEventInfo
import androidx.navigationevent.compose.NavigationBackHandler
import androidx.navigationevent.compose.rememberNavigationEventState
import dev.liau.kmppractice.ui.component.bottomsheet.BottomSheetSceneStrategy
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable
import kotlin.time.Clock
import kotlin.time.Instant

@Serializable
private data object RouteA

@Serializable
private data class RouteB(val id: String)

@Serializable
private data object RouteC

@Serializable
private data object RouteD

fun getCurrentTimeMillis(): Long {
    val now: Instant = Clock.System.now()
    return now.toEpochMilliseconds()
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavigationApp(modifier: Modifier = Modifier) {
    val backStack = remember { mutableStateListOf<Any>(RouteA) }

    val bottomSheetStrategy = remember { BottomSheetSceneStrategy<Any>() }
    NavDisplay(
        backStack = backStack,
//        onBack = {
//            backStack.removeLastOrNull()
//        },
        sceneStrategy = bottomSheetStrategy,
        entryProvider = entryProvider {
            contentRouteA(backStack)
            entry<RouteB> { key ->
                ContentDummy(
                    title = "Route id: ${key.id} ",
                    onNext = { backStack.add(RouteC) },
                    modifier = modifier

                ) {
                    Button(onClick = dropUnlessResumed {
                        if (backStack.size > 1) backStack.removeLast()
                    }) {
                        Text("Click to go back")
                    }

                }
            }
            entry<RouteC> {
                val state = rememberPullToRefreshState()
                var isRefreshing by remember { mutableStateOf(false) }
                val coroutineScope = rememberCoroutineScope()

                var offset by remember { mutableStateOf(Offset.Zero) }
                val onRefresh: () -> Unit = {
                    isRefreshing = true
                    coroutineScope.launch {
                        // Simulate network call
                        delay(1500)
                        isRefreshing = false
                    }
                }
                PullToRefreshBox(
                    modifier = modifier,
                    isRefreshing = isRefreshing,
                    onRefresh = onRefresh,
                    state = state,

                    ) {
                    Column(
                        Modifier.fillMaxSize()
                            .background(MaterialTheme.colorScheme.onSecondary)
                            .verticalScroll(rememberScrollState()),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        Text("Route C")
                        if (!isRefreshing) {

                            Button(onClick = {
                                backStack.add(RouteD)
                            }) {
                                Text("Bottom Sheet")
                            }
                            Button(onClick = {
                                backStack.clear()
                                backStack.add(RouteA)
                            }) {
                                Text("Click to go Home")
                            }
                            Button(onClick = onRefresh) {
                                Text("Refresh Data")
                            }
                        }
                    }
                }


            }
            entry<RouteD>(
                metadata = BottomSheetSceneStrategy.bottomSheet()
            ) {
                ContentDummy("Route D") {
                    Button(onClick = {
                        backStack.removeLastOrNull()
                    }) {
                        Text("Item $it")
                    }
                }
            }
        }
    )
}

@OptIn(ExperimentalComposeUiApi::class)
fun EntryProviderScope<Any>.contentRouteA(backStack: SnapshotStateList<Any>) {
    entry<RouteA> {
        var showDialog by remember { mutableStateOf(false) }
        val lastBackPressed = remember { mutableStateOf<Long>(0) }


        /*
        * Deprecated function replace using NavigationBackHandler()
        BackHandler(enabled = !showDialog) {
            showDialog = true // Intercept to show confirmation
        }*/
        NavigationBackHandler(
            state = rememberNavigationEventState(NavigationEventInfo.None),
            isBackEnabled = true, // You can toggle this dynamically
            onBackCompleted = {
                showDialog = true
                //back pressed logic here
            }
        )
        ContentDummy("Welcome to Nav3") {
            Button(onClick = {
                backStack.add(RouteB("123"))
            }) {
                Text("Click to navigate")
            }
        }
        if (showDialog) {
            AlertDialog(
                onDismissRequest = { showDialog = false },
                title = { Text("Exit?") },
                confirmButton = {
                    Button(onClick = { backStack.removeLastOrNull() }) { Text("Yes") }
                },
                dismissButton = {
                    Button(onClick = { showDialog = false }) { Text("No") }
                }
            )
        }

    }
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