package com.growfolio.app.domain.repository

import com.growfolio.app.domain.model.User

interface UserRepository {
    suspend fun getCurrentUser(): Result<User>
    suspend fun updateProfile(name: String): Result<User>
}