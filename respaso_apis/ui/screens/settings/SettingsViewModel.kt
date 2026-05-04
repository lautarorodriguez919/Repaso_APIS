package com.example.apilistapp.ui.screens.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.apilistapp.data.repository.FavoriteRepository
import com.example.apilistapp.ui.screens.list.ShowMode
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class SettingsUiState(
    val isDarkMode: Boolean = false,
    val showMode: ShowMode = ShowMode.LIST,
    val showDeleteDialog: Boolean = false
)

class SettingsViewModel : ViewModel() {

    private val repository = FavoriteRepository()

    private val _state = MutableStateFlow(SettingsUiState())
    val state: StateFlow<SettingsUiState> = _state.asStateFlow()

    fun toggleDarkMode() {
        _state.value = _state.value.copy(
            isDarkMode = !_state.value.isDarkMode
        )
    }

    fun setShowMode(mode: ShowMode) {
        _state.value = _state.value.copy(showMode = mode)
    }

    fun showDeleteDialog() {
        _state.value = _state.value.copy(showDeleteDialog = true)
    }

    fun dismissDeleteDialog() {
        _state.value = _state.value.copy(showDeleteDialog = false)
    }

    fun deleteAllFavorites() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteAllFavorites()
            dismissDeleteDialog()
        }
    }
}
