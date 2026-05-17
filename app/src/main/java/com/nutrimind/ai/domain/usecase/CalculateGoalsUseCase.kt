package com.nutrimind.ai.domain.usecase

import com.nutrimind.ai.domain.model.FitnessGoal
import com.nutrimind.ai.domain.model.UserProfile
import javax.inject.Inject

class CalculateGoalsUseCase @Inject constructor() {
    operator fun invoke(profile: UserProfile): UserProfile {
        // Mifflin-St Jeor Equation
        val s = if (profile.gender.lowercase() == "male") 5 else -161
        val bmr = (10 * profile.weight) + (6.25f * profile.height) - (5 * profile.age) + s
        
        val tdee = bmr * profile.activityLevel.multiplier
        
        val calorieGoal = when (profile.fitnessGoal) {
            FitnessGoal.WEIGHT_LOSS -> (tdee - 500).toInt()
            FitnessGoal.WEIGHT_GAIN -> (tdee + 500).toInt()
            FitnessGoal.MAINTAIN_WEIGHT -> tdee.toInt()
        }
        
        // Simplified macros (40% carb, 30% protein, 30% fat)
        val proteinGoal = (calorieGoal * 0.30f) / 4f
        val fatGoal = (calorieGoal * 0.30f) / 9f
        val carbGoal = (calorieGoal * 0.40f) / 4f
        
        return profile.copy(
            dailyCalorieGoal = calorieGoal,
            proteinGoal = proteinGoal,
            fatGoal = fatGoal,
            carbGoal = carbGoal
        )
    }
}
