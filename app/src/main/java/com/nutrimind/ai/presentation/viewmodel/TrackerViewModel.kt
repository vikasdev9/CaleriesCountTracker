package com.nutrimind.ai.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nutrimind.ai.domain.model.FoodEntry
import com.nutrimind.ai.domain.model.MealType
import com.nutrimind.ai.domain.repository.FoodRepository
import com.nutrimind.ai.domain.usecase.SearchFoodUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TrackerViewModel @Inject constructor(
    private val searchFoodUseCase: SearchFoodUseCase,
    private val foodRepository: FoodRepository
) : ViewModel() {

    private val _searchResults = MutableStateFlow<List<FoodEntry>>(emptyList())
    val searchResults = _searchResults.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    fun searchFood(query: String) {
        if (query.isBlank()) return
        viewModelScope.launch {
            _isLoading.value = true
            _searchResults.value = searchFoodUseCase.byQuery(query)
            _isLoading.value = false
        }
    }

    fun addFood(entry: FoodEntry, mealType: MealType) {
        viewModelScope.launch {
            foodRepository.insertFoodEntry(entry.copy(mealType = mealType))
        }
    }
}
