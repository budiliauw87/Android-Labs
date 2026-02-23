package dev.liau.kmppractice

import CustomSnackbarVisuals
import SnackbarManager
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.Help
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.example.compose.AppTheme
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun App(
    isDarkTheme: Boolean = false,
    snackbarManager: SnackbarManager = SnackbarManager,
) {
    AppTheme(darkTheme = isDarkTheme) {
        val navController = rememberNavController()
        val startDestination = Destination.HOME
        var selectedDestination by rememberSaveable { mutableIntStateOf(startDestination.ordinal) }
        val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())

        //navdrawer
        val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
        val scope = rememberCoroutineScope()

        //snackbar
        val snackbarHostState = remember { SnackbarHostState() }

        var showContent by remember { mutableStateOf(false) }

        LaunchedEffect(selectedDestination){
            showContent = navController.currentDestination?.route == "home"
            AppLogger.d("Side Effect", "selectedDestination: $selectedDestination")
        }
        LaunchedEffect(Unit){
            snackbarManager.snackbarEvents.collect { event ->
                snackbarHostState.currentSnackbarData?.dismiss()
                val result = snackbarHostState.showSnackbar(event.visuals)
                event.result.complete(result)
            }
        }

        ModalNavigationDrawer(
            gesturesEnabled = drawerState.isOpen,
            drawerContent = {
                ModalDrawerSheet(
                    modifier = Modifier.width(300.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(horizontal = 16.dp)
                            .verticalScroll(rememberScrollState())
                    ) {
                        Spacer(Modifier.height(12.dp))
                        Text(
                            "Drawer Title",
                            modifier = Modifier.padding(16.dp),
                            style = MaterialTheme.typography.titleLarge
                        )
                        HorizontalDivider()

                        Text(
                            "Section 1",
                            modifier = Modifier.padding(16.dp),
                            style = MaterialTheme.typography.titleMedium
                        )
                        NavigationDrawerItem(
                            label = { Text("Item 1") },
                            selected = false,
                            onClick = { /* Handle click */ }
                        )
                        NavigationDrawerItem(
                            label = { Text("Item 2") },
                            selected = false,
                            onClick = { /* Handle click */ }
                        )

                        HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))

                        Text(
                            "Section 2",
                            modifier = Modifier.padding(16.dp),
                            style = MaterialTheme.typography.titleMedium
                        )
                        NavigationDrawerItem(
                            label = { Text("Settings") },
                            selected = false,
                            icon = { Icon(Icons.Outlined.Settings, contentDescription = null) },
                            badge = { Text("20") }, // Placeholder
                            onClick = { /* Handle click */ }
                        )
                        NavigationDrawerItem(
                            label = { Text("Help and feedback") },
                            selected = false,
                            icon = {
                                Icon(
                                    Icons.AutoMirrored.Outlined.Help,
                                    contentDescription = null
                                )
                            },
                            onClick = { /* Handle click */ },
                        )
                        Spacer(Modifier.height(12.dp))
                    }
                }
            },
            drawerState = drawerState
        ) {
            Scaffold(

                topBar = {
                    CenterAlignedTopAppBar(
                        modifier = Modifier.shadow(4.dp),
                        title = {
                            Text(
                                text = stringResource(Destination.entries[selectedDestination].label),
                                maxLines = 1,
                            )
                        },

                        navigationIcon = {
                            IconButton(onClick = {
                                scope.launch {
                                    drawerState.apply {
                                        if (isClosed) open() else close()
                                    }
                                }
                            }) {
                                Icon(
                                    imageVector = Icons.Filled.Menu,
                                    contentDescription = "Menu Application"
                                )
                            }
                        },

                        scrollBehavior = scrollBehavior,
                    )
                },
                bottomBar = {
                    NavigationBar(windowInsets = NavigationBarDefaults.windowInsets) {
                        Destination.entries.forEachIndexed { index, destination ->
                            NavigationBarItem(
                                selected = selectedDestination == index,
                                onClick = {
                                    if (destination.route != navController.currentDestination?.route) {
                                        navController.navigate(route = destination.route)
                                        selectedDestination = index
                                    }
                                },
                                icon = {
                                    Icon(
                                        imageVector = destination.icon,
                                        contentDescription = stringResource(destination.contentDescription)
                                    )
                                },
                                label = { Text(stringResource(destination.label)) }
                            )
                        }
                    }
                },
                snackbarHost = {
                    SnackbarHost(hostState = snackbarHostState)
                },
                floatingActionButton = {
                    AnimatedVisibility(showContent) {
                        FloatingActionButton(onClick = {
                            /** handle click */
                            scope.launch {
                                val snackbar = SnackbarManager.showSnackBar(
                                    CustomSnackbarVisuals(
                                        "This is example snackbar trigger from floating button",
                                        "This label",
                                        true,
                                        SnackbarDuration.Short
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
                            Icon(Icons.Default.Add, contentDescription = "Add")
                        }
                    }

                },

            ) { contentPadding ->
                AppNavHost(
                    navController,
                    startDestination,
                    modifier = Modifier.padding(contentPadding)
                )
            }
        }

    }
}

@Composable
fun RowItemMenu(title: String, modifier: Modifier = Modifier) {
    Row(modifier = Modifier.padding(8.dp)) {
        Text(title)
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

