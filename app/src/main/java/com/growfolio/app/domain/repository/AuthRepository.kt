package com.growfolio.app.domain.repository

import com.growfolio.app.data.remote.dto.UnifiedTokenResponse
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    fun getAuthToken(): Flow<String?>
    suspend fun loginWithApple(identityToken: String, authorizationCode: String): Result<UnifiedTokenResponse>
    suspend fun logout()
}