package com.growfolio.app.domain.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Stock(
    val symbol: String,
    val name: String,
    val exchange: String? = null,
    val assetType: AssetType = AssetType.STOCK,
    val currentPrice: Double? = null,
    val priceChange: Double? = null,
    val priceChangePercent: Double? = null,
    val previousClose: Double? = null,
    val openPrice: Double? = null,
    val dayHigh: Double? = null,
    val dayLow: Double? = null,
    val weekHigh52: Double? = null,
    val weekLow52: Double? = null,
    val volume: Long? = null,
    val averageVolume: Long? = null,
    val marketCap: Double? = null,
    val peRatio: Double? = null,
    val dividendYield: Double? = null,
    val eps: Double? = null,
    val beta: Double? = null,
    val sector: String? = null,
    val industry: String? = null,
    val companyDescription: String? = null,
    @SerialName("websiteUrl")
    val websiteUrl: String? = null,
    @SerialName("logoUrl")
    val logoUrl: String? = null,
    val currencyCode: String = "USD",
    val lastUpdated: String? = null
)

@Serializable
enum class AssetType {
    @SerialName("stock") STOCK,
    @SerialName("etf") ETF,
    @SerialName("mutualFund") MUTUAL_FUND,
    @SerialName("bond") BOND,
    @SerialName("reit") REIT,
    @SerialName("crypto") CRYPTO,
    @SerialName("commodity") COMMODITY,
    @SerialName("option") OPTION,
    @SerialName("other") OTHER
}

@Serializable
data class StockQuote(
    val symbol: String,
    val price: Double,
    val change: Double,
    val changePercent: Double,
    val volume: Long,
    val timestamp: String
)
