package com.nutrimind.ai.data.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import com.nutrimind.ai.domain.model.ActivityLevel
import com.nutrimind.ai.domain.model.FitnessGoal
import com.nutrimind.ai.domain.model.UserProfile
import com.nutrimind.ai.domain.repository.UserRepository
import com.google.gson.Gson
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user_prefs")

class UserRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context,
    private val gson: Gson
) : UserRepository {

    private val USER_PROFILE_KEY = stringPreferencesKey("user_profile")

    override fun getUserProfile(): Flow<UserProfile?> {
        return context.dataStore.data.map { preferences ->
            val json = preferences[USER_PROFILE_KEY]
            if (json != null) {
                gson.fromJson(json, UserProfile::class.java)
            } else {
                null
            }
        }
    }

    override suspend fun saveUserProfile(profile: UserProfile) {
        context.dataStore.edit { preferences ->
            preferences[USER_PROFILE_KEY] = gson.toJson(profile)
        }
    }

    override suspend fun clearUserProfile() {
        context.dataStore.edit { it.clear() }
    }
}
