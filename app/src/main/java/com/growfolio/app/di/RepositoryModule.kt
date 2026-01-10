package com.growfolio.app.di

import com.growfolio.app.data.repository.PortfolioRepositoryImpl
import com.growfolio.app.domain.repository.PortfolioRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindPortfolioRepository(
        impl: PortfolioRepositoryImpl
    ): PortfolioRepository

    @Binds
    @Singleton
    abstract fun bindAuthRepository(
        impl: com.growfolio.app.data.repository.AuthRepositoryImpl
    ): com.growfolio.app.domain.repository.AuthRepository

    @Binds
    @Singleton
    abstract fun bindStocksRepository(
        impl: com.growfolio.app.data.repository.StocksRepositoryImpl
    ): com.growfolio.app.domain.repository.StocksRepository

    @Binds
    @Singleton
    abstract fun bindAIInsightsRepository(
        impl: com.growfolio.app.data.repository.AIInsightsRepositoryImpl
    ): com.growfolio.app.domain.repository.AIInsightsRepository

    @Binds
    @Singleton
    abstract fun bindFundingRepository(
        impl: com.growfolio.app.data.repository.FundingRepositoryImpl
    ): com.growfolio.app.domain.repository.FundingRepository

    @Binds
    @Singleton
    abstract fun bindUserRepository(
        impl: com.growfolio.app.data.repository.UserRepositoryImpl
    ): com.growfolio.app.domain.repository.UserRepository
}
