package com.growfolio.app.data.repository

import com.growfolio.app.data.local.AuthDataStore
import com.growfolio.app.data.mock.MockData
import com.growfolio.app.data.remote.GrowfolioApi
import com.growfolio.app.data.remote.dto.BasketResponse
import com.growfolio.app.domain.model.Portfolio
import com.growfolio.app.domain.repository.PortfolioRepository
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class PortfolioRepositoryImpl @Inject constructor(
    private val api: GrowfolioApi,
    private val authDataStore: AuthDataStore
) : PortfolioRepository {

    private suspend fun isMockMode(): Boolean {
        return authDataStore.authToken.first() == "mock_access_token"
    }

    override suspend fun getPortfolios(): Result<List<Portfolio>> {
        if (isMockMode()) return Result.success(MockData.portfolios)
        
        return try {
            val baskets = api.getBaskets()
            Result.success(baskets.map { it.toPortfolio() })
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getPortfolio(id: String): Result<Portfolio> {
        if (isMockMode()) {
            return MockData.portfolios.find { it.id == id }?.let { Result.success(it) }
                ?: Result.failure(Exception("Not found"))
        }

        return try {
            val basket = api.getBasket(id)
            Result.success(basket.toPortfolio())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    private fun BasketResponse.toPortfolio(): Portfolio {
        return Portfolio(
            id = this.id,
            userId = this.userId,
            name = this.name,
            description = this.description,
            currencyCode = "USD", // Default
            totalValue = this.summary?.currentValue?.toDoubleOrNull() ?: 0.0,
            totalCostBasis = this.summary?.totalInvested?.toDoubleOrNull() ?: 0.0,
            createdAt = this.createdAt,
            updatedAt = this.updatedAt,
            colorHex = this.color ?: "#007AFF",
            iconName = this.icon ?: "briefcase.fill",
            holdings = emptyList() // Allocations not mapped to Holdings for now
        )
    }
}
