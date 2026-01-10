package com.growfolio.app.domain.repository

import com.growfolio.app.domain.model.Stock

interface StocksRepository {
    suspend fun searchStocks(query: String): Result<List<Stock>>
    suspend fun getStock(symbol: String): Result<Stock>
}