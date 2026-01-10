package com.growfolio.app.presentation.stocks

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
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class StocksViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var viewModel: StocksViewModel
    private val stocksRepository = mockk<StocksRepository>()

    @Before
    fun setUp() {
        viewModel = StocksViewModel(stocksRepository)
    }

    @Test
    fun `searchStocks with blank query clears results`() = runTest {
        viewModel.searchStocks("")
        advanceUntilIdle()
        
        viewModel.uiState.test {
            val state = awaitItem()
            assertThat(state.searchResults).isEmpty()
            assertThat(state.isLoading).isFalse()
        }
    }

    @Test
    fun `searchStocks success updates searchResults`() = runTest {
        val stocks = listOf(Stock(symbol = "AAPL", name = "Apple Inc."))
        coEvery { stocksRepository.searchStocks("AAPL") } returns Result.success(stocks)

        viewModel.uiState.test {
            // Initial state
            assertThat(awaitItem().isLoading).isFalse()
            
            viewModel.searchStocks("AAPL")
            
            // Loading state
            assertThat(awaitItem().isLoading).isTrue()
            
            advanceUntilIdle()
            
            // Success state
            val successState = awaitItem()
            assertThat(successState.isLoading).isFalse()
            assertThat(successState.searchResults).isEqualTo(stocks)
        }
    }

    @Test
    fun `searchStocks failure updates error`() = runTest {
        val error = "Search failed"
        coEvery { stocksRepository.searchStocks("AAPL") } returns Result.failure(Exception(error))

        viewModel.uiState.test {
            assertThat(awaitItem().isLoading).isFalse()
            
            viewModel.searchStocks("AAPL")
            
            assertThat(awaitItem().isLoading).isTrue()
            
            advanceUntilIdle()
            
            val errorState = awaitItem()
            assertThat(errorState.isLoading).isFalse()
            assertThat(errorState.error).isEqualTo(error)
        }
    }
}
