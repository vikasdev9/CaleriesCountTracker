package com.nutrimind.ai.domain.usecase

import com.nutrimind.ai.domain.model.ActivityLevel
import com.nutrimind.ai.domain.model.FitnessGoal
import com.nutrimind.ai.domain.model.UserProfile
import org.junit.Assert.assertEquals
import org.junit.Test

class CalculateGoalsUseCaseTest {

    private val calculateGoalsUseCase = CalculateGoalsUseCase()

    @Test
    fun `calculate goals for male weight loss`() {
        val profile = UserProfile(
            gender = "male",
            weight = 80f,
            height = 180f,
            age = 25,
            activityLevel = ActivityLevel.MODERATE,
            fitnessGoal = FitnessGoal.WEIGHT_LOSS
        )
        
        val result = calculateGoalsUseCase(profile)
        
        // BMR = (10*80) + (6.25*180) - (5*25) + 5 = 800 + 1125 - 125 + 5 = 1805
        // TDEE = 1805 * 1.55 = 2797.75
        // Goal = 2797.75 - 500 = 2297
        
        assertEquals(2297, result.dailyCalorieGoal)
    }
}
