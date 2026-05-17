package com.nutrimind.ai.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nutrimind.ai.domain.model.UserProfile
import com.nutrimind.ai.domain.repository.UserRepository
import com.nutrimind.ai.domain.usecase.CalculateGoalsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val calculateGoalsUseCase: CalculateGoalsUseCase
) : ViewModel() {

    private val _profile = MutableStateFlow(UserProfile())
    val profile: StateFlow<UserProfile> = _profile.asStateFlow()

    init {
        viewModelScope.launch {
            userRepository.getUserProfile().collectLatest { savedProfile ->
                savedProfile?.let { _profile.value = it }
            }
        }
    }

    fun updateProfile(newProfile: UserProfile) {
        _profile.value = newProfile
    }

    fun saveProfile() {
        viewModelScope.launch {
            val updatedProfile = calculateGoalsUseCase(_profile.value)
            userRepository.saveUserProfile(updatedProfile)
        }
    }
}
