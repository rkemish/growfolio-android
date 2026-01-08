package com.growfolio.app.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AppleTokenExchangeRequest(
    val identityToken: String,
    val authorizationCode: String,
    val fullName: AppleFullName? = null
)

@Serializable
data class AppleFullName(
    val givenName: String?,
    val familyName: String?
)

@Serializable
data class AuthResponse(
    val token: String,
    val refreshToken: String,
    val userId: String,
    val userEmail: String
)
