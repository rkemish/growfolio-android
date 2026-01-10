package com.growfolio.app.domain.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Stock(
    @SerialName("symbol") val symbol: String,
    @SerialName("name") val name: String,
    @SerialName("exchange") val exchange: String? = null,
    @SerialName("asset_class") val assetClass: String? = null,
    @SerialName("current_price") val currentPrice: String? = null, // Decimal string
    @SerialName("change_point") val priceChange: String? = null, // Decimal string
    @SerialName("change_pct") val priceChangePercent: String? = null, // Decimal string
    @SerialName("open_price") val openPrice: String? = null, // Decimal string
    @SerialName("high_price") val dayHigh: String? = null, // Decimal string
    @SerialName("low_price") val dayLow: String? = null, // Decimal string
    @SerialName("volume") val volume: Long? = null,
    @SerialName("market_cap") val marketCap: String? = null,
    @SerialName("pe_ratio") val peRatio: String? = null,
    @SerialName("dividend_yield") val dividendYield: String? = null,
    @SerialName("eps") val eps: String? = null,
    @SerialName("beta") val beta: String? = null,
    @SerialName("sector") val sector: String? = null,
    @SerialName("industry") val industry: String? = null,
    @SerialName("description") val companyDescription: String? = null,
    @SerialName("website") val websiteUrl: String? = null,
    @SerialName("logo_url") val logoUrl: String? = null,
    
    // Status flags
    @SerialName("tradable") val tradable: Boolean = true,
    @SerialName("fractionable") val fractionable: Boolean = true,
    @SerialName("marginable") val marginable: Boolean = false,
    @SerialName("shortable") val shortable: Boolean = false
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
