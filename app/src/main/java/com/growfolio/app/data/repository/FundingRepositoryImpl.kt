package com.growfolio.app.data.repository

import com.growfolio.app.data.local.AuthDataStore
import com.growfolio.app.data.mock.MockData
import com.growfolio.app.data.remote.GrowfolioApi
import com.growfolio.app.data.remote.dto.FundingDetailsResponse
import com.growfolio.app.data.remote.dto.FundingWalletResponse
import com.growfolio.app.data.remote.dto.FundingWalletTransferResponse
import com.growfolio.app.data.remote.dto.FundingWalletWithdrawalRequest
import com.growfolio.app.data.remote.dto.RecipientBank
import com.growfolio.app.domain.repository.FundingRepository
import kotlinx.coroutines.flow.first
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FundingRepositoryImpl @Inject constructor(
    private val api: GrowfolioApi,
    private val authDataStore: AuthDataStore
) : FundingRepository {

    private suspend fun isMockMode(): Boolean {
        // Simple check for mock mode (could be improved)
        return authDataStore.authToken.first() == "mock_access_token"
    }

    override suspend fun getFundingWallet(): Result<FundingWalletResponse> {
        if (isMockMode()) return Result.success(MockData.fundingWallet)
        return try {
            Result.success(api.getFundingWallet())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getRecipientBanks(): Result<List<RecipientBank>> {
        if (isMockMode()) return Result.success(MockData.recipientBanks)
        return try {
            Result.success(api.getRecipientBanks())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getFundingDetails(currency: String?): Result<FundingDetailsResponse> {
        if (isMockMode()) return Result.success(MockData.fundingDetails)
        return try {
            Result.success(api.getDepositInstructions(currency))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getTransfers(): Result<List<FundingWalletTransferResponse>> {
        if (isMockMode()) return Result.success(MockData.fundingTransfers)
        return try {
            Result.success(api.getFundingWalletTransfers())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun withdraw(amount: Double, recipientBankId: String): Result<FundingWalletTransferResponse> {
        if (isMockMode()) {
            return Result.success(
                FundingWalletTransferResponse(
                    id = "mock_tx_new",
                    walletId = "fw_123",
                    amount = amount.toString(),
                    currency = "USD",
                    direction = "WITHDRAWAL",
                    status = "pending",
                    createdAt = "2026-01-08T12:00:00Z"
                )
            )
        }
        return try {
            Result.success(
                api.withdrawFromWallet(
                    FundingWalletWithdrawalRequest(
                        amount = amount.toString(),
                        recipientBankId = recipientBankId
                    )
                )
            )
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
