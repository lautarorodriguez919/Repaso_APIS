package com.example.apilistapp.ui.screens.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.apilistapp.data.repository.ApiRepository
import com.example.apilistapp.domain.SWCharacter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

enum class ShowMode { LIST, GRID }

data class ListUiState(
    val books: List<SWCharacter> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
    val query: String = "subject:self-help"
)

class ListViewModel : ViewModel() {

    private val repository = ApiRepository()

    private val _state = MutableStateFlow(ListUiState())
    val state: StateFlow<ListUiState> = _state.asStateFlow()

    init {
        searchBooks("subject:self-help")
    }

    fun onQueryChange(query: String) {
        _state.value = _state.value.copy(query = query)
        if (query.length >= 2) {
            searchBooks(query)
        }
    }

    fun searchBooks(query: String) {
        viewModelScope.launch(Dispatchers.IO) {
            withContext(Dispatchers.Main) {
                _state.value = _state.value.copy(isLoading = true, error = null)
            }
            try {
                val books = repository.searchBooks(query)
                withContext(Dispatchers.Main) {
                    _state.value = _state.value.copy(
                        books = books,
                        isLoading = false
                    )
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    _state.value = _state.value.copy(
                        error = "Error: ${e.message}",
                        isLoading = false
                    )
                }
            }
        }
    }
}
