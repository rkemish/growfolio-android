package com.growfolio.app.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FundingWalletResponse(
    @SerialName("id") val id: String,
    @SerialName("account_number") val accountNumber: String,
    @SerialName("status") val status: String,
    @SerialName("currency") val currency: String,
    @SerialName("balance") val balance: String
)

@Serializable
data class FundingDetailsResponse(
    @SerialName("beneficiary_name") val beneficiaryName: String,
    @SerialName("iban") val iban: String? = null,
    @SerialName("bic") val bic: String? = null,
    @SerialName("account_number") val accountNumber: String? = null,
    @SerialName("sort_code") val sortCode: String? = null,
    @SerialName("routing_number") val routingNumber: String? = null,
    @SerialName("bank_name") val bankName: String,
    @SerialName("bank_address") val bankAddress: String? = null,
    @SerialName("reference") val reference: String
)

@Serializable
data class FundingWalletTransferResponse(
    @SerialName("id") val id: String,
    @SerialName("wallet_id") val walletId: String,
    @SerialName("amount") val amount: String,
    @SerialName("currency") val currency: String,
    @SerialName("direction") val direction: String, // DEPOSIT, WITHDRAWAL
    @SerialName("status") val status: String,
    @SerialName("created_at") val createdAt: String
)

@Serializable
data class FundingWalletWithdrawalRequest(
    @SerialName("amount") val amount: String,
    @SerialName("recipient_bank_id") val recipientBankId: String
)

@Serializable
data class RecipientBank(
    @SerialName("id") val id: String,
    @SerialName("bank_name") val bankName: String,
    @SerialName("account_number") val accountNumber: String,
    @SerialName("currency") val currency: String
)