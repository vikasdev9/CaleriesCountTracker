package com.nutrimind.ai.domain.model

data class UserProfile(
    val id: String = "",
    val name: String = "",
    val age: Int = 0,
    val height: Float = 0f, // in cm
    val weight: Float = 0f, // in kg
    val gender: String = "",
    val activityLevel: ActivityLevel = ActivityLevel.MODERATE,
    val healthConditions: List<String> = emptyList(),
    val allergies: List<String> = emptyList(),
    val dietPreference: String = "",
    val waterIntakeGoal: Int = 2000, // in ml
    val fitnessGoal: FitnessGoal = FitnessGoal.MAINTAIN_WEIGHT,
    val dailyCalorieGoal: Int = 2000,
    val carbGoal: Float = 0f,
    val proteinGoal: Float = 0f,
    val fatGoal: Float = 0f
)

enum class ActivityLevel(val multiplier: Float, val description: String) {
    SEDENTARY(1.2f, "Little or no exercise"),
    LIGHTLY_ACTIVE(1.375f, "1-3 days/week"),
    MODERATE(1.55f, "3-5 days/week"),
    VERY_ACTIVE(1.725f, "6-7 days/week"),
    EXTRA_ACTIVE(1.9f, "Very hard exercise/job")
}

enum class FitnessGoal(val description: String) {
    WEIGHT_LOSS("Weight Loss"),
    WEIGHT_GAIN("Weight Gain"),
    MAINTAIN_WEIGHT("Maintain Weight")
}
