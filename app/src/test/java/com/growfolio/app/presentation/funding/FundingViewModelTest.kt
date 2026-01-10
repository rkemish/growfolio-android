package com.growfolio.app.presentation.funding

import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import com.growfolio.app.data.remote.dto.BankAccount
import com.growfolio.app.data.remote.dto.Transaction
import com.growfolio.app.domain.repository.FundingRepository
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
class FundingViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var viewModel: FundingViewModel
    private val repository = mockk<FundingRepository>()

    @Before
    fun setUp() {
        coEvery { repository.getBankAccounts() } returns Result.success(emptyList())
        coEvery { repository.getTransactions() } returns Result.success(emptyList())
    }

    @Test
    fun `loadData success updates accounts and transactions`() = runTest {
        val accounts = listOf(BankAccount("1", "Chase", "Checking", "Checking", "1234", 5000.0))
        val transactions = listOf(Transaction("1", "2026-01-08", "Deposit", 100.0, "completed", "deposit"))
        
        coEvery { repository.getBankAccounts() } returns Result.success(accounts)
        coEvery { repository.getTransactions() } returns Result.success(transactions)

        viewModel = FundingViewModel(repository)
        advanceUntilIdle()

        viewModel.uiState.test {
            val state = awaitItem()
            assertThat(state.bankAccounts).isEqualTo(accounts)
            assertThat(state.transactions).isEqualTo(transactions)
            assertThat(state.isLoading).isFalse()
        }
    }

    @Test
    fun `loadData partial failure updates error`() = runTest {
        coEvery { repository.getBankAccounts() } returns Result.failure(Exception("Bank API Error"))
        
        viewModel = FundingViewModel(repository)
        advanceUntilIdle()

        viewModel.uiState.test {
            val state = awaitItem()
            assertThat(state.error).isNotNull()
            assertThat(state.isLoading).isFalse()
        }
    }
}
