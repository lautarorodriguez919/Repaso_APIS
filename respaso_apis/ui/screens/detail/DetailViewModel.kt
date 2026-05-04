package com.example.apilistapp.ui.screens.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.apilistapp.data.repository.ApiRepository
import com.example.apilistapp.data.repository.FavoriteRepository
import com.example.apilistapp.domain.SWCharacter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

data class DetailUiState(
    val book: SWCharacter? = null,
    val isLoading: Boolean = true,
    val error: String? = null,
    val isFavorite: Boolean = false
)

class DetailViewModel(private val bookId: String) : ViewModel() {

    private val apiRepository = ApiRepository()
    private val favRepository = FavoriteRepository()

    private val _state = MutableStateFlow(DetailUiState())
    val state: StateFlow<DetailUiState> = _state.asStateFlow()

    init {
        loadBook()
    }

    private fun loadBook() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val book = apiRepository.getBookById(bookId)
                val isFavorite = favRepository.isFavorite(bookId)
                withContext(Dispatchers.Main) {
                    _state.value = DetailUiState(
                        book = book,
                        isLoading = false,
                        isFavorite = isFavorite
                    )
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    _state.value = DetailUiState(
                        error = "Error: ${e.message}",
                        isLoading = false
                    )
                }
            }
        }
    }

    fun refreshFavoriteStatus() {
        viewModelScope.launch(Dispatchers.IO) {
            val isFavorite = favRepository.isFavorite(bookId)
            withContext(Dispatchers.Main) {
                _state.value = _state.value.copy(isFavorite = isFavorite)
            }
        }
    }

    fun toggleFavorite() {
        val book = _state.value.book ?: return
        viewModelScope.launch(Dispatchers.IO) {
            if (_state.value.isFavorite) {
                favRepository.deleteFavorite(book)
            } else {
                favRepository.saveAsFavorite(book)
            }
            withContext(Dispatchers.Main) {
                _state.value = _state.value.copy(
                    isFavorite = !_state.value.isFavorite
                )
            }
        }
    }
}
