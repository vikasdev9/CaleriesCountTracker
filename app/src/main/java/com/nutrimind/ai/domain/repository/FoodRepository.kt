package com.nutrimind.ai.domain.repository

import com.nutrimind.ai.domain.model.FoodEntry
import kotlinx.coroutines.flow.Flow

interface FoodRepository {
    fun getDailyEntries(startOfDay: Long, endOfDay: Long): Flow<List<FoodEntry>>
    fun getEntriesInRange(startTime: Long, endTime: Long): Flow<List<FoodEntry>>
    suspend fun insertFoodEntry(entry: FoodEntry)
    suspend fun deleteFoodEntry(entryId: String)
    suspend fun searchFoodRemote(query: String): List<FoodEntry>
    suspend fun getFoodByBarcode(barcode: String): FoodEntry?
}
