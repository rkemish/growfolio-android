package com.growfolio.app.domain.repository

import com.growfolio.app.data.remote.dto.FundingDetailsResponse
import com.growfolio.app.data.remote.dto.FundingWalletResponse
import com.growfolio.app.data.remote.dto.FundingWalletTransferResponse
import com.growfolio.app.data.remote.dto.RecipientBank

interface FundingRepository {
    suspend fun getFundingWallet(): Result<FundingWalletResponse>
    suspend fun getRecipientBanks(): Result<List<RecipientBank>>
    suspend fun getFundingDetails(currency: String? = null): Result<FundingDetailsResponse>
    suspend fun getTransfers(): Result<List<FundingWalletTransferResponse>>
    suspend fun withdraw(amount: Double, recipientBankId: String): Result<FundingWalletTransferResponse>
}