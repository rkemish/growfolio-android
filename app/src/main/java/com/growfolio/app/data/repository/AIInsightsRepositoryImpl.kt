package com.growfolio.app.data.repository

import com.growfolio.app.data.local.AuthDataStore
import com.growfolio.app.data.mock.MockData
import com.growfolio.app.data.remote.GrowfolioApi
import com.growfolio.app.data.remote.dto.ChatMessage
import com.growfolio.app.data.remote.dto.ChatRequest
import com.growfolio.app.data.remote.dto.ChatResponse
import com.growfolio.app.data.remote.dto.InsightCard
import com.growfolio.app.domain.repository.AIInsightsRepository
import kotlinx.coroutines.flow.first
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AIInsightsRepositoryImpl @Inject constructor(
    private val api: GrowfolioApi,
    private val authDataStore: AuthDataStore
) : AIInsightsRepository {

    private suspend fun isMockMode(): Boolean {
        return authDataStore.authToken.first() == "mock_access_token"
    }

    override suspend fun sendMessage(message: String, history: List<ChatMessage>): Result<ChatResponse> {
        if (isMockMode()) return Result.success(ChatResponse("This is a mock response from your AI advisor."))
        return try {
            Result.success(api.sendMessage(ChatRequest(message, history)))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getInsights(): Result<List<InsightCard>> {
        if (isMockMode()) return Result.success(MockData.insights)
        return try {
            Result.success(api.getInsights())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
