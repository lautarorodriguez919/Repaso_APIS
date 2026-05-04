package com.example.apilistapp.ui.screens.favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.apilistapp.data.repository.FavoriteRepository
import com.example.apilistapp.domain.SWCharacter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FavoritesViewModel : ViewModel() {

    private val repository = FavoriteRepository()

    private val _favorites = MutableStateFlow<List<SWCharacter>>(emptyList())
    val favorites: StateFlow<List<SWCharacter>> = _favorites.asStateFlow()

    init {
        loadFavorites()
    }

    fun loadFavorites() {
        viewModelScope.launch(Dispatchers.IO) {
            val list = repository.getFavorites()
            withContext(Dispatchers.Main) {
                _favorites.value = list
            }
        }
    }

    fun deleteFavorite(book: SWCharacter) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteFavorite(book)
            loadFavorites()
        }
    }
}
