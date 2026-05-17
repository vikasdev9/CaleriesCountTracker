package com.nutrimind.ai.domain.model

data class NutritionAnalysis(
    val healthScore: Int, // 0-100
    val rating: FoodRating,
    val summary: String,
    val highlights: List<String>, // e.g., "High Protein", "High Sugar"
    val risks: List<String>,
    val recommendations: String,
    val alternatives: List<String>
)

enum class FoodRating {
    GOOD, BAD, WORST
}
