package com.growfolio.app.data.remote

import com.growfolio.app.data.remote.dto.*
import com.growfolio.app.domain.model.Portfolio
import com.growfolio.app.domain.model.Stock
import com.growfolio.app.domain.model.User
import retrofit2.http.*

interface GrowfolioApi {

    // Auth
    @POST("v1/auth/token/v2")
    suspend fun exchangeAppleToken(@Body request: UnifiedTokenRequest): UnifiedTokenResponse

    // User
    @GET("v1/users/me")
    suspend fun getCurrentUser(): User

    @PUT("v1/users/me")
    suspend fun updateProfile(@Body updates: Map<String, String>): User

    // Baskets
    @GET("v1/baskets")
    suspend fun getBaskets(): List<BasketResponse>

    @GET("v1/baskets/{id}")
    suspend fun getBasket(@Path("id") id: String): BasketResponse

    @POST("v1/baskets")
    suspend fun createBasket(@Body request: BasketCreate): BasketResponse

    // DCA
    @GET("v1/dca/schedules")
    suspend fun getDCASchedules(): List<DCAScheduleResponse>

    @POST("v1/dca/schedules")
    suspend fun createDCASchedule(@Body request: DCAScheduleCreate): DCAScheduleResponse

    // Orders
    @POST("v1/orders")
    suspend fun submitOrder(@Body request: SubmitOrderRequest): OrderResponse

    // Stocks
    @GET("v1/stocks/search")
    suspend fun searchStocks(@Query("q") query: String, @Query("limit") limit: Int = 10): List<Stock>

    @GET("v1/stocks/{symbol}")
    suspend fun getStock(@Path("symbol") symbol: String): Stock

    // AI Insights
    @POST("v1/ai/chat")
    suspend fun sendMessage(@Body request: ChatRequest): ChatResponse

    @GET("v1/ai/insights")
    suspend fun getInsights(): List<InsightCard>

    // Funding Wallet
    @GET("v1/funding-wallet/recipient-bank")
    suspend fun getRecipientBanks(): List<RecipientBank>

    @POST("v1/funding-wallet/recipient-bank")
    suspend fun createRecipientBank(@Body bank: RecipientBank): RecipientBank

    @POST("v1/funding-wallet/withdraw")
    suspend fun withdrawFromWallet(@Body request: FundingWalletWithdrawalRequest): FundingWalletTransferResponse

    @GET("v1/funding-wallet/transfers")
    suspend fun getFundingWalletTransfers(): List<FundingWalletTransferResponse>

    @GET("v1/funding-wallet/funding-details")
    suspend fun getDepositInstructions(@Query("currency") currency: String? = null): FundingDetailsResponse

    @GET("v1/funding-wallet")
    suspend fun getFundingWallet(): FundingWalletResponse
}
