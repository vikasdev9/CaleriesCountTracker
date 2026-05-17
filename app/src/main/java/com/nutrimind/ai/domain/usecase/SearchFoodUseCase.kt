package com.nutrimind.ai.domain.usecase

import com.nutrimind.ai.domain.model.FoodEntry
import com.nutrimind.ai.domain.repository.FoodRepository
import javax.inject.Inject

class SearchFoodUseCase @Inject constructor(
    private val foodRepository: FoodRepository
) {
    suspend fun byQuery(query: String): List<FoodEntry> {
        return foodRepository.searchFoodRemote(query)
    }

    suspend fun byBarcode(barcode: String): FoodEntry? {
        return foodRepository.getFoodByBarcode(barcode)
    }
}
