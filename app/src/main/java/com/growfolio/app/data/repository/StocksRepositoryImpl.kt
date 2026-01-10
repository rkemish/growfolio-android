package com.growfolio.app.data.repository

import com.growfolio.app.data.local.AuthDataStore
import com.growfolio.app.data.mock.MockData
import com.growfolio.app.data.remote.GrowfolioApi
import com.growfolio.app.domain.model.Stock
import com.growfolio.app.domain.repository.StocksRepository
import kotlinx.coroutines.flow.first
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StocksRepositoryImpl @Inject constructor(
    private val api: GrowfolioApi,
    private val authDataStore: AuthDataStore
) : StocksRepository {

    private suspend fun isMockMode(): Boolean {
        // Simple mock check
        return authDataStore.authToken.first() == "mock_access_token"
    }

    override suspend fun searchStocks(query: String): Result<List<Stock>> {
        if (isMockMode()) {
            return Result.success(MockData.stocks.filter { 
                it.symbol.contains(query, ignoreCase = true) || it.name.contains(query, ignoreCase = true) 
            })
        }
        return try {
            Result.success(api.searchStocks(query))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getStock(symbol: String): Result<Stock> {
        if (isMockMode()) {
            return MockData.stocks.find { it.symbol == symbol }?.let { Result.success(it) }
                ?: Result.failure(Exception("Stock not found"))
        }
        return try {
            Result.success(api.getStock(symbol))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
