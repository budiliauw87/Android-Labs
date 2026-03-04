package dev.liau.kmppractice.ui.navigation

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.dropUnlessResumed
import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.ui.NavDisplay
import dev.liau.kmppractice.ui.component.bottomsheet.BottomSheetSceneStrategy
import kotlinx.serialization.Serializable

@Serializable
private data object RouteA
@Serializable
private data class RouteB(val id: String)
@Serializable
private data object RouteC

@Serializable
private data object RouteD

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavigationApp() {
    val backStack = remember { mutableStateListOf<Any>(RouteA) }
    val bottomSheetStrategy = remember { BottomSheetSceneStrategy<Any>() }
    NavDisplay(
        backStack = backStack,
        onBack = {
            if (backStack.size > 1) {
                // Navigate back within the app
                backStack.removeLast()
            }
        },
        sceneStrategy = bottomSheetStrategy,
        entryProvider = entryProvider {
            contentRouteA(backStack)
            entry<RouteB> {key->
                ContentDummy(
                    title="Route id: ${key.id} ",
                    onNext = {backStack.add(RouteC)}

                ){
                    Button(onClick = dropUnlessResumed{
                        if (backStack.size > 1) backStack.removeLast()
                    }) {
                        Text("Click to go back")
                    }

                }
            }
            entry<RouteC>{
                ContentDummy("Route C"){
                    Column ( horizontalAlignment = Alignment.CenterHorizontally){
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
                    }
                }
            }
            entry<RouteD>(
                metadata = BottomSheetSceneStrategy.bottomSheet()
            ) {
                ContentDummy("Route D"){
                    Column{
                        repeat(10){
                            if( it == 10){
                                Button(onClick = {
                                    backStack.removeLastOrNull()
                                }) {
                                    Text("Close Bottom Sheet")
                                }
                            }
                            Button(onClick = {
                                backStack.removeLastOrNull()
                            }) {
                                Text("Item $it")
                            }

                        }

                    }
                }
            }
        }
    )
}

fun EntryProviderScope<Any>.contentRouteA(backStack: SnapshotStateList<Any>) {
    entry<RouteA> {
        ContentDummy("Welcome to Nav3") {
            Button(onClick = {
                backStack.add(RouteB("123"))
            }) {
                Text("Click to navigate")
            }
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