package com.nutrimind.ai.di

import com.nutrimind.ai.data.repository.AiRepositoryImpl
import com.nutrimind.ai.data.repository.AuthRepositoryImpl
import com.nutrimind.ai.data.repository.FoodRepositoryImpl
import com.nutrimind.ai.data.repository.UserRepositoryImpl
import com.nutrimind.ai.domain.repository.AiRepository
import com.nutrimind.ai.domain.repository.AuthRepository
import com.nutrimind.ai.domain.repository.FoodRepository
import com.nutrimind.ai.domain.repository.UserRepository
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
    abstract fun bindFoodRepository(impl: FoodRepositoryImpl): FoodRepository

    @Binds
    @Singleton
    abstract fun bindUserRepository(impl: UserRepositoryImpl): UserRepository

    @Binds
    @Singleton
    abstract fun bindAiRepository(impl: AiRepositoryImpl): AiRepository

    @Binds
    @Singleton
    abstract fun bindAuthRepository(impl: AuthRepositoryImpl): AuthRepository
}
