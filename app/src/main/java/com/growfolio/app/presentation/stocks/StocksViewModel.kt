package com.growfolio.app.presentation.stocks

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.growfolio.app.domain.model.Stock
import com.growfolio.app.domain.repository.StocksRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StocksViewModel @Inject constructor(
    private val stocksRepository: StocksRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(StocksState())
    val uiState = _uiState.asStateFlow()

    fun searchStocks(query: String) {
        if (query.isBlank()) {
            _uiState.update { it.copy(searchResults = emptyList()) }
            return
        }

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            val result = stocksRepository.searchStocks(query)
            result.onSuccess { stocks ->
                _uiState.update { it.copy(isLoading = false, searchResults = stocks) }
            }.onFailure { error ->
                _uiState.update { it.copy(isLoading = false, error = error.message) }
            }
        }
    }
}

data class StocksState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val searchResults: List<Stock> = emptyList(),
    val watchlist: List<Stock> = emptyList() // TODO: Implement watchlist local storage
)
