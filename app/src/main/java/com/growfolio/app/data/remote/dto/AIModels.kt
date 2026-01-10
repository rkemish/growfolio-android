package com.growfolio.app.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ChatRequest(
    @SerialName("message") val message: String,
    @SerialName("conversation_history") val conversationHistory: List<ChatMessage>? = null,
    @SerialName("include_portfolio_context") val includePortfolioContext: Boolean = true
)

@Serializable
data class ChatResponse(
    @SerialName("message") val message: String,
    @SerialName("suggested_actions") val suggestedActions: List<String>? = null
)

@Serializable
data class ChatMessage(
    @SerialName("role") val role: String, // "user" or "assistant"
    @SerialName("content") val content: String,
    @SerialName("timestamp") val timestamp: String? = null
)

@Serializable
data class InsightCard(
    @SerialName("id") val id: String,
    @SerialName("title") val title: String,
    @SerialName("description") val description: String,
    @SerialName("type") val type: String, // "tip", "alert", "opportunity"
    @SerialName("symbol") val symbol: String? = null
)