package com.nutrimind.ai.data.repository

import com.google.ai.client.generativeai.GenerativeModel
import com.nutrimind.ai.domain.model.FoodRating
import com.nutrimind.ai.domain.model.NutritionAnalysis
import com.nutrimind.ai.domain.model.UserProfile
import com.nutrimind.ai.domain.repository.AiRepository
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AiRepositoryImpl @Inject constructor(
    private val generativeModel: GenerativeModel,
    private val gson: Gson
) : AiRepository {

    override suspend fun calculateHealthInsights(profile: UserProfile): String {
        val prompt = "Based on this user profile: $profile, calculate daily calorie needs, BMI, and suggest protein/carbs/fat intake."
        val response = generativeModel.generateContent(prompt)
        return response.text ?: "Could not generate insights."
    }

    override suspend fun analyzeFood(foodName: String): NutritionAnalysis {
        val prompt = "Analyze the nutrition of $foodName. Return a JSON with healthScore (0-100), rating (GOOD, BAD, or WORST), summary, highlights (list), risks (list), recommendations, and alternatives (list)."
        val response = generativeModel.generateContent(prompt)
        return try {
            gson.fromJson(response.text, NutritionAnalysis::class.java)
        } catch (e: Exception) {
            fallbackAnalysis(foodName)
        }
    }

    override suspend fun analyzeIngredients(ocrText: String): NutritionAnalysis {
        val prompt = "Analyze these ingredients from a product label: $ocrText. Return a JSON with healthScore (0-100), rating (GOOD, BAD, or WORST), summary, highlights (list), risks (list), recommendations, and alternatives (list)."
        val response = generativeModel.generateContent(prompt)
        return try {
            gson.fromJson(response.text, NutritionAnalysis::class.java)
        } catch (e: Exception) {
            fallbackAnalysis("Scanned Ingredients")
        }
    }

    override fun chatWithAssistant(message: String): Flow<String> = flow {
        val response = generativeModel.generateContent(message)
        emit(response.text ?: "I am sorry, I cannot answer that.")
    }

    private fun fallbackAnalysis(name: String) = NutritionAnalysis(
        healthScore = 50,
        rating = FoodRating.GOOD,
        summary = "Analysis for $name is unavailable.",
        highlights = emptyList(),
        risks = emptyList(),
        recommendations = "Consult a nutritionist for detailed info.",
        alternatives = emptyList()
    )
}
