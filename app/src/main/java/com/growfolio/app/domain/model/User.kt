package com.growfolio.app.domain.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class User(
    val id: String,
    val email: String,
    val displayName: String? = null,
    val firstName: String? = null,
    val lastName: String? = null,
    @SerialName("profilePictureUrl")
    val profilePictureUrl: String? = null,
    val alpacaAccountId: String? = null,
    val familyId: String? = null,
    val preferredCurrency: String = "USD",
    val notificationsEnabled: Boolean = true,
    val biometricEnabled: Boolean = false,
    val createdAt: String, // ISO8601 String
    val updatedAt: String,
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
