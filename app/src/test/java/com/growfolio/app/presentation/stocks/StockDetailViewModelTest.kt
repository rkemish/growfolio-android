package com.growfolio.app.presentation.stocks

import androidx.lifecycle.SavedStateHandle
import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import com.growfolio.app.domain.model.Stock
import com.growfolio.app.domain.repository.StocksRepository
import com.growfolio.app.util.MainDispatcherRule
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class StockDetailViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var viewModel: StockDetailViewModel
    private val repository = mockk<StocksRepository>()
    private val savedStateHandle = SavedStateHandle(mapOf("symbol" to "AAPL"))

    @Test
    fun `loadStockDetail success updates state with stock`() = runTest {
        val stock = Stock(
            symbol = "AAPL",
            name = "Apple",
            currentPrice = "150.0",
            priceChange = "2.0",
            priceChangePercent = "1.3"
        )
        
        coEvery { repository.getStock("AAPL") } returns Result.success(stock)

        viewModel = StockDetailViewModel(repository, savedStateHandle)
        advanceUntilIdle()

        viewModel.uiState.test {
            val state = awaitItem()
            assertThat(state.isLoading).isFalse()
            assertThat(state.stock).isEqualTo(stock)
            assertThat(state.error).isNull()
        }
    }

    @Test
    fun `loadStockDetail failure updates error state`() = runTest {
        val error = "Not Found"
        coEvery { repository.getStock("AAPL") } returns Result.failure(Exception(error))

        viewModel = StockDetailViewModel(repository, savedStateHandle)
        advanceUntilIdle()

        viewModel.uiState.test {
            val state = awaitItem()
            assertThat(state.isLoading).isFalse()
            assertThat(state.error).isEqualTo(error)
        }
    }
}