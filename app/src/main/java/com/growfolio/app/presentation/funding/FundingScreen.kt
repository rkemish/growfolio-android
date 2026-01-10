package com.growfolio.app.presentation.funding

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.growfolio.app.data.remote.dto.FundingWalletResponse
import com.growfolio.app.data.remote.dto.FundingWalletTransferResponse
import com.growfolio.app.data.remote.dto.RecipientBank

@Composable
fun FundingScreen(
    viewModel: FundingViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            Box(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
                Text("Funding Wallet", style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold)
            }
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { /* TODO: Add Recipient Bank */ }) {
                Icon(Icons.Default.Add, contentDescription = "Add Bank")
            }
        }
    ) { padding ->
        if (state.isLoading && state.fundingWallet == null) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .padding(padding)
                    .fillMaxSize()
                    .padding(horizontal = 16.dp)
            ) {
                // Wallet Header
                item {
                    state.fundingWallet?.let { wallet ->
                        WalletCard(wallet)
                        Spacer(modifier = Modifier.height(24.dp))
                    }
                }

                item {
                    Text("Recipient Banks", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(8.dp))
                }

                if (state.recipientBanks.isEmpty()) {
                    item {
                        Text("No recipient banks linked.", style = MaterialTheme.typography.bodyMedium, color = Color.Gray)
                        Spacer(modifier = Modifier.height(16.dp))
                    }
                } else {
                    items(state.recipientBanks) { bank ->
                        RecipientBankItem(bank)
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                }

                item {
                    Spacer(modifier = Modifier.height(24.dp))
                    Text("Recent Transfers", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(8.dp))
                }

                if (state.transfers.isEmpty()) {
                    item {
                        Text("No transfer history.", style = MaterialTheme.typography.bodyMedium, color = Color.Gray)
                    }
                } else {
                    items(state.transfers) { transfer ->
                        TransferItem(transfer)
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                }
            }
        }
    }
}

@Composable
fun WalletCard(wallet: FundingWalletResponse) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Available Balance", style = MaterialTheme.typography.labelMedium)
            Text(
                "${wallet.currency} ${wallet.balance}",
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text("Account Number: ${wallet.accountNumber}", style = MaterialTheme.typography.bodyMedium)
        }
    }
}

@Composable
fun RecipientBankItem(bank: RecipientBank) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
            Column(modifier = Modifier.weight(1f)) {
                Text(bank.bankName, fontWeight = FontWeight.Bold)
                Text("**** ${bank.accountNumber} (${bank.currency})", style = MaterialTheme.typography.bodySmall)
            }
        }
    }
}

@Composable
fun TransferItem(transfer: FundingWalletTransferResponse) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
            Column(modifier = Modifier.weight(1f)) {
                Text(transfer.direction, fontWeight = FontWeight.Medium)
                Text(transfer.status, style = MaterialTheme.typography.bodySmall)
            }
            val color = if (transfer.direction == "WITHDRAWAL") Color.Red else Color(0xFF4CAF50)
            Text(
                text = "${if (transfer.direction == "DEPOSIT") "+" else "-"}${transfer.amount} ${transfer.currency}",
                color = color,
                fontWeight = FontWeight.Bold
            )
        }
    }
}