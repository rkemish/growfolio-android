package com.growfolio.app.presentation.funding

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.growfolio.app.data.remote.dto.FundingDetailsResponse
import com.growfolio.app.data.remote.dto.FundingWalletResponse
import com.growfolio.app.data.remote.dto.FundingWalletTransferResponse
import com.growfolio.app.data.remote.dto.RecipientBank
import com.growfolio.app.domain.repository.FundingRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FundingViewModel @Inject constructor(
    private val repository: FundingRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(FundingState())
    val uiState = _uiState.asStateFlow()

    init {
        loadData()
    }

    fun loadData() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            
            // Parallel fetch could be better but sequential is safer for now
            val walletResult = repository.getFundingWallet()
            val banksResult = repository.getRecipientBanks()
            val transfersResult = repository.getTransfers()

            _uiState.update { 
                it.copy(
                    isLoading = false,
                    fundingWallet = walletResult.getOrNull(),
                    recipientBanks = banksResult.getOrDefault(emptyList()),
                    transfers = transfersResult.getOrDefault(emptyList()),
                    error = if (walletResult.isFailure) "Failed to load wallet" else null
                ) 
            }
        }
    }

    fun withdraw(amount: Double, recipientBankId: String) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            val result = repository.withdraw(amount, recipientBankId)
            result.onSuccess {
                loadData() // Refresh
            }.onFailure { error ->
                _uiState.update { it.copy(isLoading = false, error = error.message) }
            }
        }
    }
}

data class FundingState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val fundingWallet: FundingWalletResponse? = null,
    val recipientBanks: List<RecipientBank> = emptyList(),
    val transfers: List<FundingWalletTransferResponse> = emptyList(),
    val depositInstructions: FundingDetailsResponse? = null
)