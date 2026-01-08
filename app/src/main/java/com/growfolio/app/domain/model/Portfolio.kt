package com.growfolio.app.domain.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Portfolio(
    val id: String,
    val userId: String,
    val name: String,
    val description: String? = null,
    val type: PortfolioType = PortfolioType.PERSONAL,
    val currencyCode: String = "USD",
    val totalValue: Double = 0.0,
    val totalCostBasis: Double = 0.0,
    val cashBalance: Double = 0.0,
    val lastValuationDate: String? = null,
    val isDefault: Boolean = false,
    val colorHex: String = "#007AFF",
    val iconName: String = "briefcase.fill",
    val createdAt: String,
    val updatedAt: String
) {
    val totalReturn: Double
        get() = totalValue - totalCostBasis
        
    val totalReturnPercentage: Double
        get() = if (totalCostBasis > 0) ((totalValue - totalCostBasis) / totalCostBasis) * 100.0 else 0.0
}

@Serializable
enum class PortfolioType {
    @SerialName("personal") PERSONAL,
    @SerialName("retirement") RETIREMENT,
    @SerialName("education") EDUCATION,
    @SerialName("brokerage") BROKERAGE,
    @SerialName("ira") IRA,
    @SerialName("roth") ROTH,
    @SerialName("hsa") HSA,
    @SerialName("trust") TRUST,
    @SerialName("joint") JOINT,
    @SerialName("custodial") CUSTODIAL,
    @SerialName("other") OTHER
}
