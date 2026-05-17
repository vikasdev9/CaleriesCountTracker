package com.nutrimind.ai.data.repository

import com.nutrimind.ai.domain.model.UserProfile
import com.nutrimind.ai.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor() : AuthRepository {

    override val currentUser: Flow<UserProfile?> = MutableStateFlow(
        UserProfile(id = "dummy_id", name = "Guest User")
    )

    override suspend fun login(email: String, pass: String): Result<Unit> {
        return Result.success(Unit)
    }

    override suspend fun signup(email: String, pass: String, name: String): Result<Unit> {
        return Result.success(Unit)
    }

    override suspend fun googleSignIn(idToken: String): Result<Unit> {
        return Result.success(Unit)
    }

    override suspend fun logout() {
        // No-op
    }

    override suspend fun forgotPassword(email: String): Result<Unit> {
        return Result.success(Unit)
    }
}
