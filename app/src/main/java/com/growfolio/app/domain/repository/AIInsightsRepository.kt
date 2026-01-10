package com.growfolio.app.domain.repository

import com.growfolio.app.data.remote.dto.ChatMessage
import com.growfolio.app.data.remote.dto.ChatResponse
import com.growfolio.app.data.remote.dto.InsightCard

interface AIInsightsRepository {
    suspend fun sendMessage(message: String, history: List<ChatMessage>): Result<ChatResponse>
    suspend fun getInsights(): Result<List<InsightCard>>
}
