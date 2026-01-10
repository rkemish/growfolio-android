package com.growfolio.app.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SubmitOrderRequest(
    @SerialName("symbol") val symbol: String,
    @SerialName("side") val side: String, // buy, sell
    @SerialName("type") val type: String, // market, limit, etc.
    @SerialName("time_in_force") val timeInForce: String,
    @SerialName("qty") val qty: String? = null,
    @SerialName("notional") val notional: String? = null,
    @SerialName("limit_price") val limitPrice: String? = null,
    @SerialName("stop_price") val stopPrice: String? = null,
    @SerialName("client_order_id") val clientOrderId: String? = null
)

@Serializable
data class OrderResponse(
    @SerialName("id") val id: String,
    @SerialName("client_order_id") val clientOrderId: String? = null,
    @SerialName("symbol") val symbol: String,
    @SerialName("side") val side: String,
    @SerialName("type") val type: String,
    @SerialName("status") val status: String,
    @SerialName("filled_qty") val filledQty: String? = null,
    @SerialName("filled_avg_price") val filledAvgPrice: String? = null,
    @SerialName("submitted_at") val submittedAt: String
)