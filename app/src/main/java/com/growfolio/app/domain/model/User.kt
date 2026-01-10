package com.growfolio.app.domain.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class User(
    @SerialName("id") val id: String,
    @SerialName("email") val email: String?,
    @SerialName("name") val name: String? = null,
    @SerialName("first_name") val firstName: String? = null,
    @SerialName("last_name") val lastName: String? = null,
    @SerialName("picture_url") val profilePictureUrl: String? = null,
    @SerialName("alpaca_account_id") val alpacaAccountId: String? = null,
    @SerialName("family_id") val familyId: String? = null,
    @SerialName("created_at") val createdAt: String? = null,
    @SerialName("updated_at") val updatedAt: String? = null,

    // Fields not in basic UserResponse (handled via Preferences or local defaults)
    val preferredCurrency: String = "USD",
    val notificationsEnabled: Boolean = true,
    val biometricEnabled: Boolean = false,
    val subscriptionTier: SubscriptionTier = SubscriptionTier.FREE,
    val subscriptionExpiresAt: String? = null,
    val timezoneIdentifier: String = "UTC"
)

@Serializable
enum class SubscriptionTier {
    @SerialName("free") FREE,
    @SerialName("premium") PREMIUM,
    @SerialName("family") FAMILY
}