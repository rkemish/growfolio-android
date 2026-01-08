package com.growfolio.app.data.repository

import com.growfolio.app.data.remote.GrowfolioApi
import com.growfolio.app.domain.model.Portfolio
import com.growfolio.app.domain.repository.PortfolioRepository
import javax.inject.Inject

class PortfolioRepositoryImpl @Inject constructor(
    private val api: GrowfolioApi
) : PortfolioRepository {

    override suspend fun getPortfolios(): Result<List<Portfolio>> {
        return try {
            val portfolios = api.getPortfolios()
            Result.success(portfolios)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getPortfolio(id: String): Result<Portfolio> {
        return try {
            val portfolio = api.getPortfolio(id)
            Result.success(portfolio)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
