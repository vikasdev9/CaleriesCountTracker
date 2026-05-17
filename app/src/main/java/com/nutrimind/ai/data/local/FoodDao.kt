package com.nutrimind.ai.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface FoodDao {
    @Query("SELECT * FROM food_entries WHERE timestamp >= :startOfDay AND timestamp < :endOfDay")
    fun getDailyEntries(startOfDay: Long, endOfDay: Long): Flow<List<FoodEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFoodEntry(entity: FoodEntity)

    @Query("DELETE FROM food_entries WHERE id = :entryId")
    suspend fun deleteFoodEntry(entryId: String)

    @Query("SELECT * FROM food_entries WHERE timestamp BETWEEN :startTime AND :endTime")
    fun getEntriesInRange(startTime: Long, endTime: Long): Flow<List<FoodEntity>>
}
