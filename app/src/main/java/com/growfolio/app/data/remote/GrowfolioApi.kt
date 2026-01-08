package com.growfolio.app.data.remote

import com.growfolio.app.data.remote.dto.AppleTokenExchangeRequest
import com.growfolio.app.data.remote.dto.AuthResponse
import com.growfolio.app.domain.model.Portfolio
import com.growfolio.app.domain.model.Stock
import com.growfolio.app.domain.model.StockQuote
import com.growfolio.app.domain.model.User
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface GrowfolioApi {

    // Auth
    @POST("v1/auth/token")
    suspend fun exchangeAppleToken(@Body request: AppleTokenExchangeRequest): AuthResponse

    // User
    @GET("v1/users/me")
    suspend fun getCurrentUser(): User

    // Portfolios
    @GET("v1/portfolios")
    suspend fun getPortfolios(): List<Portfolio>

    @GET("v1/portfolios/{id}")
    suspend fun getPortfolio(@Path("id") id: String): Portfolio

    // Stocks
    @GET("v1/stocks/search")
    suspend fun searchStocks(@Query("q") query: String, @Query("limit") limit: Int = 10): List<Stock>

    @GET("v1/stocks/{symbol}")
    suspend fun getStock(@Path("symbol") symbol: String): Stock

    @GET("v1/stocks/{symbol}/quote")
    suspend fun getStockQuote(@Path("symbol") symbol: String): StockQuote
}
