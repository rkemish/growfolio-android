package com.growfolio.app.domain.repository

import com.growfolio.app.domain.model.Portfolio
import kotlinx.coroutines.flow.Flow

interface PortfolioRepository {
    suspend fun getPortfolios(): Result<List<Portfolio>>
    suspend fun getPortfolio(id: String): Result<Portfolio>
}
