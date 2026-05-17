package com.nutrimind.ai.domain.repository

import com.nutrimind.ai.domain.model.UserProfile
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    fun getUserProfile(): Flow<UserProfile?>
    suspend fun saveUserProfile(profile: UserProfile)
    suspend fun clearUserProfile()
}
