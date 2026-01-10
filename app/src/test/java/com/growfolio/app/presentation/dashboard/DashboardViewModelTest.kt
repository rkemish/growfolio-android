package com.growfolio.app.presentation.dashboard

import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import com.growfolio.app.data.remote.PriceWebSocketManager
import com.growfolio.app.data.remote.WSServerMessage
import com.growfolio.app.data.remote.dto.InsightCard
import com.growfolio.app.domain.model.Portfolio
import com.growfolio.app.domain.repository.AIInsightsRepository
import com.growfolio.app.domain.repository.PortfolioRepository
import com.growfolio.app.util.MainDispatcherRule
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class DashboardViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var viewModel: DashboardViewModel
    private val portfolioRepository = mockk<PortfolioRepository>()
    private val aiRepository = mockk<AIInsightsRepository>()
    private val webSocketManager = mockk<PriceWebSocketManager>(relaxed = true)
    private val wsEvents = MutableSharedFlow<WSServerMessage>()

    @Before
    fun setUp() {
        every { webSocketManager.events } returns wsEvents
        coEvery { portfolioRepository.getPortfolios() } returns Result.success(emptyList())
        coEvery { aiRepository.getInsights() } returns Result.success(emptyList())
    }

    @Test
    fun `loadInsights success updates uiState with insights`() = runTest {
        val insights = listOf(InsightCard("1", "Tip", "Buy low", "tip"))
        coEvery { aiRepository.getInsights() } returns Result.success(insights)

        viewModel = DashboardViewModel(portfolioRepository, aiRepository, webSocketManager)
        advanceUntilIdle()

        viewModel.uiState.test {
            val state = awaitItem()
            assertThat(state.insights).isEqualTo(insights)
        }
    }

    @Test
    fun `loadData success updates uiState with portfolios`() = runTest {
        val portfolios = listOf(
            Portfolio(
                id = "1",
                userId = "u1",
                name = "Stocks",
                holdings = emptyList(),
                totalValue = 1000.0,
                createdAt = "",
                updatedAt = ""
            )
        )
        coEvery { portfolioRepository.getPortfolios() } returns Result.success(portfolios)

        viewModel = DashboardViewModel(portfolioRepository, aiRepository, webSocketManager)
        advanceUntilIdle()

        viewModel.uiState.test {
            val state = awaitItem()
            assertThat(state.isLoading).isFalse()
            assertThat(state.portfolios).isEqualTo(portfolios)
            assertThat(state.totalValue).isEqualTo(1000.0)
        }
        
        verify { webSocketManager.connect() }
        verify { webSocketManager.subscribe(any()) }
    }

    @Test
    fun `loadData failure updates uiState with error`() = runTest {
        val errorMessage = "Network Error"
        coEvery { portfolioRepository.getPortfolios() } returns Result.failure(Exception(errorMessage))

        viewModel = DashboardViewModel(portfolioRepository, aiRepository, webSocketManager)
        advanceUntilIdle()

        viewModel.uiState.test {
            val state = awaitItem()
            assertThat(state.isLoading).isFalse()
            assertThat(state.error).isEqualTo(errorMessage)
        }
    }

    @Test
    fun `price update event changes total value`() = runTest {
        val portfolios = listOf(
            Portfolio(
                id = "1",
                userId = "u1",
                name = "Stocks",
                holdings = emptyList(),
                totalValue = 1000.0,
                createdAt = "",
                updatedAt = ""
            )
        )
        coEvery { portfolioRepository.getPortfolios() } returns Result.success(portfolios)

        viewModel = DashboardViewModel(portfolioRepository, aiRepository, webSocketManager)
        advanceUntilIdle()

        viewModel.uiState.test {
            val initialState = awaitItem()
            assertThat(initialState.totalValue).isEqualTo(1000.0)

            wsEvents.emit(WSServerMessage(id = "1", type = "event", event = "quote_updated", timestamp = ""))
            advanceUntilIdle()
            
            val updatedState = awaitItem()
            assertThat(updatedState.totalValue).isNotEqualTo(1000.0)
        }
    }
}