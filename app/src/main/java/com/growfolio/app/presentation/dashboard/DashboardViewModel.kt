package com.growfolio.app.presentation.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.growfolio.app.domain.model.Portfolio
import com.growfolio.app.domain.repository.PortfolioRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val portfolioRepository: PortfolioRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(DashboardState())
    val uiState: StateFlow<DashboardState> = _uiState.asStateFlow()

    init {
        loadData()
    }

    fun loadData() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            
            val result = portfolioRepository.getPortfolios()
            
            result.onSuccess { portfolios ->
                _uiState.update { 
                    it.copy(
                        isLoading = false,
                        portfolios = portfolios,
                        totalValue = portfolios.sumOf { p -> p.totalValue }
                    ) 
                }
            }.onFailure { error ->
                _uiState.update { 
                    it.copy(
                        isLoading = false,
                        error = error.message
                    ) 
                }
            }
        }
    }
}

data class DashboardState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val portfolios: List<Portfolio> = emptyList(),
    val totalValue: Double = 0.0
)
