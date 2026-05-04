package com.example.apilistapp.ui.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

sealed class Routes : NavKey {
    @Serializable
    data object ListScreen : Routes()

    @Serializable
    data object FavoritesScreen : Routes()

    @Serializable
    data object SettingsScreen : Routes()

    @Serializable
    data class DetailScreen(val bookId: String) : Routes()
}