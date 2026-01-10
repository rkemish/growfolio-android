package com.growfolio.app.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UnifiedTokenRequest(
    @SerialName("identity_token") val identityToken: String,
    @SerialName("provider") val provider: String = "apple",
    @SerialName("user_first_name") val userFirstName: String? = null,
    @SerialName("user_last_name") val userLastName: String? = null
)

@Serializable
data class UnifiedTokenResponse(
    @SerialName("user_id") val userId: String,
    @SerialName("email") val email: String?,
    @SerialName("name") val name: String?,
    @SerialName("is_new_user") val isNewUser: Boolean,
    @SerialName("was_linked") val wasLinked: Boolean = false,
    @SerialName("auth_provider") val authProvider: String,
    @SerialName("linked_providers") val linkedProviders: List<String> = emptyList(),
    @SerialName("alpaca_account_id") val alpacaAccountId: String? = null,
    @SerialName("alpaca_account_status") val alpacaAccountStatus: String? = null
)