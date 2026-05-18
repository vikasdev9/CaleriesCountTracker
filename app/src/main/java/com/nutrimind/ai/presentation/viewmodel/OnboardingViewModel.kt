package com.nutrimind.ai.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.nutrimind.ai.data.local.prefs.PreferenceManager
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class OnboardingViewModel @Inject constructor(
    private val preferenceManager: PreferenceManager
) : ViewModel() {

    fun completeOnboarding() {
        preferenceManager.setOnboardingCompleted(true)
    }
}
