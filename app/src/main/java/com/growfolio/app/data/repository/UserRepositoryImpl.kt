package com.growfolio.app.data.repository

import com.growfolio.app.data.local.AuthDataStore
import com.growfolio.app.data.mock.MockData
import com.growfolio.app.data.remote.GrowfolioApi
import com.growfolio.app.domain.model.User
import com.growfolio.app.domain.repository.UserRepository
import kotlinx.coroutines.flow.first
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepositoryImpl @Inject constructor(
    private val api: GrowfolioApi,
    private val authDataStore: AuthDataStore
) : UserRepository {

    private suspend fun isMockMode(): Boolean {
        return authDataStore.authToken.first() == "mock_access_token"
    }

    override suspend fun getCurrentUser(): Result<User> {
        if (isMockMode()) return Result.success(MockData.user)
        return try {
            Result.success(api.getCurrentUser())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun updateProfile(name: String): Result<User> {
        if (isMockMode()) return Result.success(MockData.user.copy(name = name))
        return try {
            Result.success(api.updateProfile(mapOf("name" to name)))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
