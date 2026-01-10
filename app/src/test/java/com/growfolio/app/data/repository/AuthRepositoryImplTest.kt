package com.growfolio.app.data.repository

import com.google.common.truth.Truth.assertThat
import com.growfolio.app.data.local.AuthDataStore
import com.growfolio.app.data.remote.GrowfolioApi
import com.growfolio.app.data.remote.dto.AuthResponse
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class AuthRepositoryImplTest {

    private lateinit var repository: AuthRepositoryImpl
    private val api = mockk<GrowfolioApi>()
    private val authDataStore = mockk<AuthDataStore>(relaxed = true)

    @Before
    fun setUp() {
        repository = AuthRepositoryImpl(api, authDataStore)
    }

    @Test
    fun `loginWithApple success saves tokens and returns success`() = runTest {
        val authResponse = AuthResponse(
            token = "token",
            refreshToken = "refresh",
            userId = "user123",
            userEmail = "test@example.com"
        )
        coEvery { api.exchangeAppleToken(any()) } returns authResponse

        val result = repository.loginWithApple("identity", "code")

        assertThat(result.isSuccess).isTrue()
        assertThat(result.getOrNull()).isEqualTo(authResponse)
        
        coVerify { 
            authDataStore.saveAuthResponse(
                token = "token",
                refreshToken = "refresh",
                userId = "user123"
            ) 
        }
    }

    @Test
    fun `loginWithApple failure returns failure`() = runTest {
        coEvery { api.exchangeAppleToken(any()) } throws Exception("Auth Failed")

        val result = repository.loginWithApple("identity", "code")

        assertThat(result.isFailure).isTrue()
        assertThat(result.exceptionOrNull()?.message).isEqualTo("Auth Failed")
    }

    @Test
    fun `logout clears authDataStore`() = runTest {
        repository.logout()
        coVerify { authDataStore.clearAuth() }
    }
}
