package com.growfolio.app.domain.model

import com.growfolio.app.domain.model.Stock
import kotlinx.serialization.Serializable

@Serializable
data class Holding(
    val id: String,
    val portfolioId: String,
    val stockSymbol: String,
    val stockName: String? = null,
    val stock: Stock = Stock(symbol = stockSymbol, name = stockName ?: ""),
    val quantity: Double,
    val averageCostPerShare: Double,
    val currentPricePerShare: Double,
    val firstPurchaseDate: String? = null,
    val lastPurchaseDate: String? = null,
    val priceUpdatedAt: String? = null,
    val sector: String? = null,
    val industry: String? = null,
    val assetType: AssetType = AssetType.STOCK,
    val createdAt: String,
    val updatedAt: String
) {
    val marketValue: Double
        get() = currentPricePerShare * quantity
        
    val costBasis: Double
        get() = averageCostPerShare * quantity
        
    val unrealizedGainLoss: Double
        get() = marketValue - costBasis
        
    val unrealizedGainLossPercentage: Double
        get() = if (costBasis > 0) ((marketValue - costBasis) / costBasis) * 100.0 else 0.0
}
