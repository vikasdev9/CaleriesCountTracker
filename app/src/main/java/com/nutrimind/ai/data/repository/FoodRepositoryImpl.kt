package com.nutrimind.ai.data.repository

import com.nutrimind.ai.data.local.FoodDao
import com.nutrimind.ai.data.local.toDomain
import com.nutrimind.ai.data.local.toEntity
import com.nutrimind.ai.data.remote.ApiService
import com.nutrimind.ai.domain.model.FoodEntry
import com.nutrimind.ai.domain.model.MealType
import com.nutrimind.ai.domain.repository.FoodRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class FoodRepositoryImpl @Inject constructor(
    private val foodDao: FoodDao,
    private val apiService: ApiService
) : FoodRepository {

    override fun getDailyEntries(startOfDay: Long, endOfDay: Long): Flow<List<FoodEntry>> {
        return foodDao.getDailyEntries(startOfDay, endOfDay).map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override fun getEntriesInRange(startTime: Long, endTime: Long): Flow<List<FoodEntry>> {
        return foodDao.getEntriesInRange(startTime, endTime).map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override suspend fun insertFoodEntry(entry: FoodEntry) {
        foodDao.insertFoodEntry(entry.toEntity())
    }

    override suspend fun deleteFoodEntry(entryId: String) {
        foodDao.deleteFoodEntry(entryId)
    }

    override suspend fun searchFoodRemote(query: String): List<FoodEntry> {
        return try {
            val response = apiService.searchFood(query)
            response.products.map { it.toDomain() }
        } catch (e: Exception) {
            emptyList()
        }
    }

    override suspend fun getFoodByBarcode(barcode: String): FoodEntry? {
        return try {
            val response = apiService.getProductByBarcode(barcode)
            if (response.status == 1 && response.product != null) {
                response.product.toDomain()
            } else null
        } catch (e: Exception) {
            null
        }
    }
}

fun com.nutrimind.ai.data.remote.ProductDto.toDomain() = FoodEntry(
    id = code ?: "",
    name = product_name ?: "Unknown",
    calories = (nutriments?.energy_100g ?: 0f).toInt(),
    carbs = nutriments?.carbohydrates_100g ?: 0f,
    protein = nutriments?.proteins_100g ?: 0f,
    fat = nutriments?.fat_100g ?: 0f,
    sugar = nutriments?.sugars_100g ?: 0f,
    sodium = nutriments?.sodium_100g ?: 0f,
    mealType = MealType.SNACK,
    imageUrl = image_url,
    barcode = code
)
