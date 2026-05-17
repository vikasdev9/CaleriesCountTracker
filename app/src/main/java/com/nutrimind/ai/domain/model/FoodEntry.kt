package com.nutrimind.ai.domain.model

data class FoodEntry(
    val id: String = "",
    val name: String,
    val calories: Int,
    val carbs: Float = 0f,
    val protein: Float = 0f,
    val fat: Float = 0f,
    val sugar: Float = 0f,
    val sodium: Float = 0f,
    val mealType: MealType,
    val timestamp: Long = System.currentTimeMillis(),
    val imageUrl: String? = null,
    val barcode: String? = null,
    val healthScore: Int = 0,
    val rating: FoodRating = FoodRating.GOOD
)

enum class MealType {
    BREAKFAST, LUNCH, DINNER, SNACK
}
