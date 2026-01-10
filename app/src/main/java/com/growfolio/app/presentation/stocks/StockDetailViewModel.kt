package com.growfolio.app.presentation.stocks

import androidx.lifecycle.SavedStateHandle
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
class StockDetailViewModel @Inject constructor(
    private val repository: StocksRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val symbol: String = checkNotNull(savedStateHandle["symbol"])
    
    private val _uiState = MutableStateFlow(StockDetailState())
    val uiState = _uiState.asStateFlow()

    init {
        loadStockDetail()
    }

    fun loadStockDetail() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            
            val stockResult = repository.getStock(symbol)

            if (stockResult.isSuccess) {
                _uiState.update { it.copy(
                    isLoading = false,
                    stock = stockResult.getOrNull()
                ) }
            } else {
                _uiState.update { it.copy(
                    isLoading = false,
                    error = stockResult.exceptionOrNull()?.message ?: "Failed to load stock details"
                ) }
            }
        }
    }
}

data class StockDetailState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val stock: Stock? = null
)