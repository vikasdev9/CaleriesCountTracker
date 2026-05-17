package com.nutrimind.ai.data.remote

import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("api/v2/search")
    suspend fun searchFood(
        @Query("search_terms") query: String,
        @Query("fields") fields: String = "product_name,nutriments,image_url,code"
    ): FoodSearchResponse
    @GET("api/v0/product/{barcode}.json")
    suspend fun getProductByBarcode(
        @retrofit2.http.Path("barcode") barcode: String
    ): BarcodeResponse
}

data class BarcodeResponse(
    val status: Int,
    val product: ProductDto?
)

data class FoodSearchResponse(
    val products: List<ProductDto>
)

data class ProductDto(
    val product_name: String?,
    val nutriments: NutrimentsDto?,
    val image_url: String?,
    val code: String?
)

data class NutrimentsDto(
    val energy_100g: Float?,
    val carbohydrates_100g: Float?,
    val proteins_100g: Float?,
    val fat_100g: Float?,
    val sugars_100g: Float?,
    val sodium_100g: Float?
)
