package com.nutrimind.ai.domain.usecase

import com.nutrimind.ai.domain.model.NutritionAnalysis
import com.nutrimind.ai.domain.repository.AiRepository
import javax.inject.Inject

class AnalyzeProductUseCase @Inject constructor(
    private val aiRepository: AiRepository
) {
    suspend fun fromText(foodName: String): NutritionAnalysis {
        return aiRepository.analyzeFood(foodName)
    }

    suspend fun fromIngredients(ocrText: String): NutritionAnalysis {
        return aiRepository.analyzeIngredients(ocrText)
    }
}
