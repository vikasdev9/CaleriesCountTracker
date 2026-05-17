package com.nutrimind.ai.domain.usecase

import com.nutrimind.ai.domain.model.FoodEntry
import com.nutrimind.ai.domain.repository.FoodRepository
import com.nutrimind.ai.utils.DateUtils
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetDailyNutritionUseCase @Inject constructor(
    private val repository: FoodRepository
) {
    operator fun invoke(): Flow<List<FoodEntry>> {
        val now = System.currentTimeMillis()
        val startOfDay = DateUtils.getStartOfDay(now)
        val endOfDay = DateUtils.getEndOfDay(now)
        
        return repository.getDailyEntries(startOfDay, endOfDay)
    }
}
