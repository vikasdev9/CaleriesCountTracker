package com.nutrimind.ai.domain.repository

import com.nutrimind.ai.domain.model.UserProfile
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    val currentUser: Flow<UserProfile?>
    suspend fun login(email: String, pass: String): Result<Unit>
    suspend fun signup(email: String, pass: String, name: String): Result<Unit>
    suspend fun googleSignIn(idToken: String): Result<Unit>
    suspend fun logout()
    suspend fun forgotPassword(email: String): Result<Unit>
}
