package com.nutrimind.ai.domain.repository

import com.nutrimind.ai.domain.model.FoodEntry
import com.nutrimind.ai.domain.model.NutritionAnalysis
import com.nutrimind.ai.domain.model.UserProfile
import kotlinx.coroutines.flow.Flow

interface AiRepository {
    suspend fun calculateHealthInsights(profile: UserProfile): String
    suspend fun analyzeFood(foodName: String): NutritionAnalysis
    suspend fun analyzeIngredients(ocrText: String): NutritionAnalysis
    fun chatWithAssistant(message: String): Flow<String>
}
