package com.growfolio.app.presentation.ai_insights

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.growfolio.app.data.remote.dto.ChatMessage
import com.growfolio.app.domain.repository.AIInsightsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AIInsightsViewModel @Inject constructor(
    private val repository: AIInsightsRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(AIInsightsState())
    val uiState = _uiState.asStateFlow()

    fun sendMessage(content: String) {
        if (content.isBlank()) return

        val userMessage = ChatMessage(role = "user", content = content)
        _uiState.update { it.copy(
            messages = it.messages + userMessage,
            isLoading = true
        ) }

        viewModelScope.launch {
            val result = repository.sendMessage(content, _uiState.value.messages)
            result.onSuccess { response ->
                val assistantMessage = ChatMessage(role = "assistant", content = response.message)
                _uiState.update { it.copy(
                    messages = it.messages + assistantMessage,
                    isLoading = false
                ) }
            }.onFailure { error ->
                _uiState.update { it.copy(
                    isLoading = false,
                    error = error.message
                ) }
            }
        }
    }
}

data class AIInsightsState(
    val messages: List<ChatMessage> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)
