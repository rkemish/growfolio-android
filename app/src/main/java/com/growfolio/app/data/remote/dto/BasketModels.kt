package com.growfolio.app.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BasketResponse(
    @SerialName("id") val id: String,
    @SerialName("user_id") val userId: String,
    @SerialName("name") val name: String,
    @SerialName("description") val description: String? = null,
    @SerialName("category") val category: String? = null,
    @SerialName("icon") val icon: String? = null,
    @SerialName("color") val color: String? = null,
    @SerialName("allocations") val allocations: List<BasketAllocation>,
    @SerialName("dca_enabled") val dcaEnabled: Boolean,
    @SerialName("dca_schedule_id") val dcaScheduleId: String? = null,
    @SerialName("status") val status: String,
    @SerialName("summary") val summary: BasketSummary? = null,
    @SerialName("is_shared") val isShared: Boolean = false,
    @SerialName("created_at") val createdAt: String,
    @SerialName("updated_at") val updatedAt: String
)

@Serializable
data class BasketAllocation(
    @SerialName("symbol") val symbol: String,
    @SerialName("name") val name: String,
    @SerialName("percentage") val percentage: String, // Decimal as String
    @SerialName("target_shares") val targetShares: String? = null // Decimal as String
)

@Serializable
data class BasketSummary(
    @SerialName("current_value") val currentValue: String? = null,
    @SerialName("total_invested") val totalInvested: String? = null,
    @SerialName("total_gain_loss") val totalGainLoss: String? = null
)

@Serializable
data class BasketCreate(
    @SerialName("name") val name: String,
    @SerialName("description") val description: String? = null,
    @SerialName("category") val category: String? = null,
    @SerialName("icon") val icon: String? = null,
    @SerialName("color") val color: String? = null,
    @SerialName("allocations") val allocations: List<BasketAllocation>,
    @SerialName("is_shared") val isShared: Boolean = false
)