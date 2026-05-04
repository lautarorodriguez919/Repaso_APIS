package com.example.apilistapp.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.apilistapp.ui.navigation.Routes

sealed class BottomBarItem(
    val route: Routes,
    val label: String,
    val icon: ImageVector
) {
    data object Home : BottomBarItem(
        route = Routes.ListScreen,
        label = "Home",
        icon = Icons.Default.Home
    )
    data object Favorites : BottomBarItem(
        route = Routes.FavoritesScreen,
        label = "Favorites",
        icon = Icons.Default.Favorite
    )
    data object Settings : BottomBarItem(
        route = Routes.SettingsScreen,
        label = "Settings",
        icon = Icons.Default.Settings
    )
}

val bottomBarItems = listOf(
    BottomBarItem.Home,
    BottomBarItem.Favorites,
    BottomBarItem.Settings
)

@Composable
fun BottomBar(
    currentRoute: Any?,
    onNavigate: (Routes) -> Unit
) {
    NavigationBar {
        bottomBarItems.forEach { item ->
            NavigationBarItem(
                selected = currentRoute?.javaClass == item.route.javaClass,
                onClick = { onNavigate(item.route) },
                icon = { Icon(item.icon, contentDescription = item.label) },
                label = { Text(item.label) }
            )
        }
    }
}