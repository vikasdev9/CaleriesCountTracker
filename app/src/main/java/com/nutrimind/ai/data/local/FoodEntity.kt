package com.nutrimind.ai.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.nutrimind.ai.domain.model.FoodEntry
import com.nutrimind.ai.domain.model.MealType

@Entity(tableName = "food_entries")
data class FoodEntity(
    @PrimaryKey val id: String,
    val name: String,
    val calories: Int,
    val carbs: Float,
    val protein: Float,
    val fat: Float,
    val sugar: Float,
    val sodium: Float,
    val mealType: String,
    val timestamp: Long,
    val imageUrl: String?,
    val barcode: String?
)

fun FoodEntity.toDomain() = FoodEntry(
    id = id,
    name = name,
    calories = calories,
    carbs = carbs,
    protein = protein,
    fat = fat,
    sugar = sugar,
    sodium = sodium,
    mealType = MealType.valueOf(mealType),
    timestamp = timestamp,
    imageUrl = imageUrl,
    barcode = barcode
)

fun FoodEntry.toEntity() = FoodEntity(
    id = id.ifEmpty { java.util.UUID.randomUUID().toString() },
    name = name,
    calories = calories,
    carbs = carbs,
    protein = protein,
    fat = fat,
    sugar = sugar,
    sodium = sodium,
    mealType = mealType.name,
    timestamp = timestamp,
    imageUrl = imageUrl,
    barcode = barcode
)
