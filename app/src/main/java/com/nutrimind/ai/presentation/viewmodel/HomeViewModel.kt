package com.nutrimind.ai.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nutrimind.ai.domain.model.FoodEntry
import com.nutrimind.ai.domain.model.UserProfile
import com.nutrimind.ai.domain.usecase.GetDailyNutritionUseCase
import com.nutrimind.ai.domain.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getDailyNutritionUseCase: GetDailyNutritionUseCase,
    private val userRepository: UserRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        loadData()
    }

    private fun loadData() {
        viewModelScope.launch {
            combine(
                userRepository.getUserProfile(),
                getDailyNutritionUseCase()
            ) { profile, entries ->
                HomeUiState(
                    userProfile = profile,
                    dailyEntries = entries,
                    consumedCalories = entries.sumOf { it.calories },
                    targetCalories = profile?.dailyCalorieGoal ?: 2000,
                    waterIntake = profile?.waterIntakeGoal ?: 2000
                )
            }.collect { state ->
                _uiState.value = state
            }
        }
    }
}

data class HomeUiState(
    val userProfile: UserProfile? = null,
    val dailyEntries: List<FoodEntry> = emptyList(),
    val consumedCalories: Int = 0,
    val targetCalories: Int = 2000,
    val waterIntake: Int = 0,
    val isLoading: Boolean = false
)
