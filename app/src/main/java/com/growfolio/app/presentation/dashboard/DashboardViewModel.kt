package com.growfolio.app.presentation.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.growfolio.app.data.remote.dto.InsightCard
import com.growfolio.app.domain.model.Portfolio
import com.growfolio.app.domain.repository.AIInsightsRepository
import com.growfolio.app.domain.repository.PortfolioRepository
import com.growfolio.app.data.remote.PriceWebSocketManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val portfolioRepository: PortfolioRepository,
    private val aiRepository: AIInsightsRepository,
    private val webSocketManager: PriceWebSocketManager
) : ViewModel() {

    private val _uiState = MutableStateFlow(DashboardState())
    val uiState: StateFlow<DashboardState> = _uiState.asStateFlow()

    init {
        loadData()
        loadInsights()
        observePriceUpdates()
    }

    private fun loadInsights() {
        viewModelScope.launch {
            aiRepository.getInsights().onSuccess { insights ->
                _uiState.update { it.copy(insights = insights) }
            }
        }
    }

    private fun observePriceUpdates() {
        webSocketManager.events
            .onEach { message ->
                if (message.event == "quote_updated") {
                    _uiState.update { state ->
                        state.copy(
                            totalValue = state.totalValue + (Math.random() - 0.5) * 5
                        )
                    }
                }
            }
            .launchIn(viewModelScope)
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
                webSocketManager.connect()
                webSocketManager.subscribe(listOf("quotes", "account", "positions", "baskets"))
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

    override fun onCleared() {
        super.onCleared()
        webSocketManager.disconnect()
    }
}

data class DashboardState(

    val isLoading: Boolean = false,

    val error: String? = null,

    val portfolios: List<Portfolio> = emptyList(),

    val totalValue: Double = 0.0,

    val insights: List<InsightCard> = emptyList()

)
