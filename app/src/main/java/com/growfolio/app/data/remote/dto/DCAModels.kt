package com.growfolio.app.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DCAScheduleResponse(
    @SerialName("id") val id: String,
    @SerialName("user_id") val userId: String,
    @SerialName("basket_id") val basketId: String? = null,
    @SerialName("name") val name: String,
    @SerialName("amount") val amount: String, // Decimal
    @SerialName("currency") val currency: String,
    @SerialName("frequency") val frequency: String,
    @SerialName("day_of_week") val dayOfWeek: Int? = null,
    @SerialName("day_of_month") val dayOfMonth: Int? = null,
    @SerialName("execution_time") val executionTime: String? = null,
    @SerialName("status") val status: String,
    @SerialName("allocations") val allocations: List<DCAAllocation>? = null,
    @SerialName("next_execution") val nextExecution: String? = null,
    @SerialName("last_execution") val lastExecution: String? = null,
    @SerialName("total_invested") val totalInvested: String? = null,
    @SerialName("created_at") val createdAt: String,
    @SerialName("updated_at") val updatedAt: String
)

@Serializable
data class DCAScheduleCreate(
    @SerialName("name") val name: String,
    @SerialName("basket_id") val basketId: String? = null,
    @SerialName("amount") val amount: String, // Decimal
    @SerialName("currency") val currency: String = "GBP",
    @SerialName("frequency") val frequency: String,
    @SerialName("day_of_week") val dayOfWeek: Int? = null,
    @SerialName("day_of_month") val dayOfMonth: Int? = null,
    @SerialName("execution_time") val executionTime: String? = "14:30:00",
    @SerialName("allocations") val allocations: List<DCAAllocation>,
    @SerialName("max_executions") val maxExecutions: Int? = null
)

@Serializable
data class DCAAllocation(
    @SerialName("symbol") val symbol: String,
    @SerialName("percentage") val percentage: String, // Decimal
    @SerialName("min_amount") val minAmount: String? = null
)