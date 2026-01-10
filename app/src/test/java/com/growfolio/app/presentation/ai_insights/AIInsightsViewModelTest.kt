package com.growfolio.app.presentation.ai_insights

import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import com.growfolio.app.data.remote.dto.ChatResponse
import com.growfolio.app.domain.repository.AIInsightsRepository
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
class AIInsightsViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var viewModel: AIInsightsViewModel
    private val repository = mockk<AIInsightsRepository>()

    @Before
    fun setUp() {
        viewModel = AIInsightsViewModel(repository)
    }

    @Test
    fun `sendMessage success updates messages with user and assistant responses`() = runTest {
        val userContent = "How is my AAPL doing?"
        val assistantContent = "AAPL is up 2% today."
        coEvery { repository.sendMessage(userContent, any()) } returns Result.success(
            ChatResponse(id = "1", message = assistantContent, timestamp = "")
        )

        viewModel.uiState.test {
            assertThat(awaitItem().isLoading).isFalse()
            
            viewModel.sendMessage(userContent)
            
            assertThat(awaitItem().isLoading).isTrue()
            
            advanceUntilIdle()
            
            val successState = awaitItem()
            assertThat(successState.isLoading).isFalse()
            assertThat(successState.messages).hasSize(2)
        }
    }

    @Test
    fun `sendMessage failure updates error state`() = runTest {
        val error = "AI Service Unavailable"
        coEvery { repository.sendMessage(any(), any()) } returns Result.failure(Exception(error))

        viewModel.uiState.test {
            assertThat(awaitItem().isLoading).isFalse()
            
            viewModel.sendMessage("Hello")
            
            assertThat(awaitItem().isLoading).isTrue()
            
            advanceUntilIdle()
            
            val errorState = awaitItem()
            assertThat(errorState.error).isEqualTo(error)
            assertThat(errorState.isLoading).isFalse()
        }
    }
}
