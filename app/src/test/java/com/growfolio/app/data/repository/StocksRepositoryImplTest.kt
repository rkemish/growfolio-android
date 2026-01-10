package com.growfolio.app.data.repository

import com.google.common.truth.Truth.assertThat
import com.growfolio.app.data.local.AuthDataStore
import com.growfolio.app.data.remote.GrowfolioApi
import com.growfolio.app.domain.model.Stock
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class StocksRepositoryImplTest {

    private lateinit var repository: StocksRepositoryImpl
    private val api = mockk<GrowfolioApi>()
    private val authDataStore = mockk<AuthDataStore>()

    @Before
    fun setUp() {
        every { authDataStore.authToken } returns flowOf(null)
        repository = StocksRepositoryImpl(api, authDataStore)
    }

    @Test
    fun `searchStocks success returns list`() = runTest {
        val stocks = listOf(Stock(symbol = "AAPL", name = "Apple"))
        coEvery { api.searchStocks("AAPL") } returns stocks

        val result = repository.searchStocks("AAPL")

        assertThat(result.isSuccess).isTrue()
        assertThat(result.getOrNull()).isEqualTo(stocks)
    }

    @Test
    fun `searchStocks mock mode returns mock data`() = runTest {
        every { authDataStore.authToken } returns flowOf("mock_access_token")
        
        val result = repository.searchStocks("AAPL")

        assertThat(result.isSuccess).isTrue()
        assertThat(result.getOrNull()?.any { it.symbol == "AAPL" }).isTrue()
    }

    @Test
    fun `searchStocks failure returns failure`() = runTest {
        coEvery { api.searchStocks(any()) } throws Exception("API Error")

        val result = repository.searchStocks("AAPL")

        assertThat(result.isFailure).isTrue()
        assertThat(result.exceptionOrNull()?.message).isEqualTo("API Error")
    }

    @Test
    fun `getStock success returns stock`() = runTest {
        val stock = Stock(symbol = "AAPL", name = "Apple")
        coEvery { api.getStock("AAPL") } returns stock

        val result = repository.getStock("AAPL")

        assertThat(result.isSuccess).isTrue()
        assertThat(result.getOrNull()).isEqualTo(stock)
    }
}