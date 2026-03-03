
package dev.liau.kmppractice

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Apps
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation3.runtime.NavKey
import dev.liau.kmppractice.ui.screens.ScreenComponent
import dev.liau.kmppractice.ui.screens.ScreenHome
import dev.liau.kmppractice.ui.screens.ScreenSetting
import kmppractice.composeapp.generated.resources.Res
import kmppractice.composeapp.generated.resources.component_desc
import kmppractice.composeapp.generated.resources.component_label
import kmppractice.composeapp.generated.resources.home_desc
import kmppractice.composeapp.generated.resources.home_label
import kmppractice.composeapp.generated.resources.setting_desc
import kmppractice.composeapp.generated.resources.setting_label
import org.jetbrains.compose.resources.StringResource


enum class Destination(
    val route: String,
    val label: StringResource,
    val icon: ImageVector,
    val contentDescription: StringResource
): NavKey {
    HOME("home", Res.string.home_label,
        Icons.Default.Home,
        Res.string.home_desc),
    COMPONENT("component",
        Res.string.component_label,
        Icons.Default.Apps,
        Res.string.component_desc),
    SETTING("setting",
        Res.string.setting_label,
        Icons.Default.Settings,
        Res.string.setting_desc)
}

@Composable
fun AppNavHost(
    navController: NavHostController,
    startDestination: Destination,
    modifier: Modifier = Modifier
){

    NavHost(
        navController,
        startDestination = startDestination.route
    ) {
        Destination.entries.forEach { destination ->
            composable(destination.route) {
                when(destination.route){
                    "home"-> ScreenHome(modifier)
                    "component"-> ScreenComponent(modifier)
                    "setting"-> ScreenSetting(modifier)
                }
            }
        }
    }

}