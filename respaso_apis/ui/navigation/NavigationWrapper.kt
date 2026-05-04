package com.example.apilistapp.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import com.example.apilistapp.ui.components.BottomBar
import com.example.apilistapp.ui.screens.detail.DetailScreen
import com.example.apilistapp.ui.screens.favorites.FavoritesScreen
import com.example.apilistapp.ui.screens.list.ListScreen
import com.example.apilistapp.ui.screens.list.ShowMode
import com.example.apilistapp.ui.screens.settings.SettingsScreen

@Composable
fun NavigationWrapper() {
    val backStack = rememberNavBackStack(Routes.ListScreen)
    val currentRoute = backStack.lastOrNull()

    var isDarkMode by remember { mutableStateOf(false) }
    var showMode by remember { mutableStateOf(ShowMode.LIST) }

    Scaffold(
        bottomBar = {
            if (currentRoute !is Routes.DetailScreen) {
                BottomBar(
                    currentRoute = currentRoute,
                    onNavigate = { route ->
                        if (currentRoute != route) {
                            backStack.clear()
                            backStack.add(route)
                        }
                    }
                )
            }
        }
    ) { innerPadding ->
        NavDisplay(
            backStack = backStack,
            modifier = androidx.compose.ui.Modifier.padding(innerPadding),
            onBack = { backStack.removeLastOrNull() },
            entryProvider = { route ->
                when (route) {
                    is Routes.ListScreen -> NavEntry(route) {
                        ListScreen(
                            navigateToDetail = { bookId ->
                                backStack.add(Routes.DetailScreen(bookId))
                            },
                            showMode = showMode
                        )
                    }
                    is Routes.FavoritesScreen -> NavEntry(route) {
                        FavoritesScreen(
                            navigateToDetail = { bookId ->
                                backStack.add(Routes.DetailScreen(bookId))
                            },
                            showMode = showMode
                        )
                    }
                    is Routes.SettingsScreen -> NavEntry(route) {
                        SettingsScreen(
                            onDarkModeChange = { isDarkMode = it },
                            onShowModeChange = { showMode = it }
                        )
                    }
                    is Routes.DetailScreen -> NavEntry(route) {
                        DetailScreen(
                            bookId = route.bookId,
                            navigateBack = { backStack.removeLastOrNull() }
                        )
                    }
                    else -> NavEntry(route) {}
                }
            }
        )
    }
}
