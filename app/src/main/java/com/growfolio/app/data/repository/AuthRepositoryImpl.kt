package com.growfolio.app.data.repository

import com.growfolio.app.data.local.AuthDataStore
import com.growfolio.app.data.remote.GrowfolioApi
import com.growfolio.app.data.remote.dto.UnifiedTokenRequest
import com.growfolio.app.data.remote.dto.UnifiedTokenResponse
import com.growfolio.app.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepositoryImpl @Inject constructor(
    private val api: GrowfolioApi,
    private val authDataStore: AuthDataStore
) : AuthRepository {

    override fun getAuthToken(): Flow<String?> = authDataStore.authToken

    override suspend fun loginWithApple(identityToken: String, authorizationCode: String): Result<UnifiedTokenResponse> {
        // Handle mock tokens for development/testing without a backend
        if (identityToken == "mock_identity_token") {
            val mockResponse = UnifiedTokenResponse(
                userId = "mock_user_123",
                email = "mock@growfolio.app",
                name = "Mock User",
                isNewUser = false,
                authProvider = "apple",
                wasLinked = false
            )
            // Use mock_access_token as the session token in mock mode
            authDataStore.saveAuthSession(
                token = "mock_access_token",
                userId = mockResponse.userId
            )
            return Result.success(mockResponse)
        }

        return try {
            val response = api.exchangeAppleToken(
                UnifiedTokenRequest(
                    identityToken = identityToken
                    // authorizationCode is not used in UnifiedTokenRequest (v2), but kept in function signature
                )
            )
            
            // IMPORTANT: v2 Auth API uses the identityToken itself as the Bearer token
            // The response does NOT contain a new access token.
            authDataStore.saveAuthSession(
                token = identityToken,
                userId = response.userId
            )
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun logout() {
        authDataStore.clearAuth()
    }
}