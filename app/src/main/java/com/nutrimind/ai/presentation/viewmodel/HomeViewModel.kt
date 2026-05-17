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
                    consumedProtein = entries.sumOf { it.protein.toInt() },
                    consumedCarbs = entries.sumOf { it.carbs.toInt() },
                    consumedFats = entries.sumOf { it.fat.toInt() },
                    waterIntake = 1200 // Mock water intake for now
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
    val consumedProtein: Int = 0,
    val consumedCarbs: Int = 0,
    val consumedFats: Int = 0,
    val waterIntake: Int = 0,
    val weeklyCalories: List<Float> = listOf(1800f, 2100f, 1950f, 2200f, 1700f, 2050f, 1900f),
    val isLoading: Boolean = false
)
