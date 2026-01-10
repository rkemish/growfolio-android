package com.growfolio.app.data.mock

import com.growfolio.app.data.remote.dto.*
import com.growfolio.app.domain.model.*

object MockData {
    val user = User(
        id = "mock_user_123",
        email = "mock@growfolio.app",
        name = "Demo User",
        firstName = "Demo",
        lastName = "User",
        createdAt = "2026-01-01T00:00:00Z",
        updatedAt = "2026-01-01T00:00:00Z"
    )

    val portfolios = listOf(
        Portfolio(
            id = "p1",
            userId = user.id,
            name = "Growth Stocks",
            totalValue = 12450.50,
            totalCostBasis = 10000.0,
            createdAt = "2026-01-01T00:00:00Z",
            updatedAt = "2026-01-01T00:00:00Z"
        ),
        Portfolio(
            id = "p2",
            userId = user.id,
            name = "Safe Haven",
            totalValue = 5200.0,
            totalCostBasis = 5000.0,
            createdAt = "2026-01-01T00:00:00Z",
            updatedAt = "2026-01-01T00:00:00Z"
        )
    )

    val stocks = listOf(
        Stock(
            symbol = "AAPL",
            name = "Apple Inc.",
            currentPrice = "185.20",
            priceChange = "2.45",
            priceChangePercent = "1.3",
            companyDescription = "Apple Inc. designs, manufactures, and markets smartphones, personal computers, tablets, wearables, and accessories worldwide."
        ),
        Stock(
            symbol = "TSLA",
            name = "Tesla, Inc.",
            currentPrice = "240.50",
            priceChange = "-5.20",
            priceChangePercent = "-2.1",
            companyDescription = "Tesla, Inc. designs, develops, manufactures, leases, and sells electric vehicles, and energy generation and storage systems."
        ),
        Stock(
            symbol = "NVDA",
            name = "NVIDIA Corporation",
            currentPrice = "480.10",
            priceChange = "12.30",
            priceChangePercent = "2.6",
            companyDescription = "NVIDIA Corporation provides graphics, and computing and networking solutions worldwide."
        )
    )

    val fundingWallet = FundingWalletResponse(
        id = "fw_123",
        accountNumber = "FW123456789",
        status = "active",
        currency = "USD",
        balance = "12500.00"
    )

    val recipientBanks = listOf(
        RecipientBank("b1", "Chase Bank", "4422", "USD"),
        RecipientBank("b2", "Barclays", "9901", "GBP")
    )

    val fundingTransfers = listOf(
        FundingWalletTransferResponse("t1", "fw_123", "1000.00", "USD", "DEPOSIT", "completed", "2026-01-07T10:00:00Z"),
        FundingWalletTransferResponse("t2", "fw_123", "500.00", "USD", "WITHDRAWAL", "completed", "2026-01-05T14:30:00Z")
    )

    val fundingDetails = FundingDetailsResponse(
        beneficiaryName = "Growfolio Client Funds",
        iban = "GB29REMI12345678901234",
        bic = "REMIGB22",
        bankName = "Revolut Ltd",
        reference = "REF-MOCK-USER"
    )

    val insights = listOf(
        InsightCard("i1", "Portfolio Optimization", "Your tech exposure is at 75%. Consider diversifying into energy.", "tip"),
        InsightCard("i2", "Earning Alert", "NVDA reports earnings in 2 days. Historically, it moves +/- 8% on earnings.", "alert")
    )
}