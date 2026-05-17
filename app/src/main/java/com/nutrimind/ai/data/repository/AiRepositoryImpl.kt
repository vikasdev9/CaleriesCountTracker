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
        val prompt = """
            Analyze the nutrition of $foodName. 
            Return ONLY a valid JSON object with these fields:
            - healthScore (0-100)
            - rating (strictly "GOOD", "BAD", or "WORST")
            - summary (brief overview)
            - highlights (list of benefits)
            - risks (list of concerns like high sugar, sodium, or preservatives)
            - recommendations (how to consume it)
            - alternatives (list of 2-3 healthier options)
            
            Be strict and scientific. If it's highly processed, rate it BAD or WORST.
        """.trimIndent()
        
        val response = generativeModel.generateContent(prompt)
        return try {
            val jsonText = response.text?.replace("```json", "")?.replace("```", "")?.trim() ?: ""
            gson.fromJson(jsonText, NutritionAnalysis::class.java)
        } catch (e: Exception) {
            fallbackAnalysis(foodName)
        }
    }

    override suspend fun analyzeIngredients(ocrText: String): NutritionAnalysis {
        val prompt = """
            Analyze these ingredients from a product label: $ocrText.
            Return ONLY a valid JSON object with these fields:
            - healthScore (0-100)
            - rating (strictly "GOOD", "BAD", or "WORST")
            - summary (brief overview of ingredient quality)
            - highlights (any good ingredients found)
            - risks (specific harmful additives, preservatives, high sugar, or sodium detected)
            - recommendations (final verdict)
            - alternatives (list of 2-3 healthier options)
            
            Be very detailed about artificial additives and preservatives.
        """.trimIndent()
        
        val response = generativeModel.generateContent(prompt)
        return try {
            val jsonText = response.text?.replace("```json", "")?.replace("```", "")?.trim() ?: ""
            gson.fromJson(jsonText, NutritionAnalysis::class.java)
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
