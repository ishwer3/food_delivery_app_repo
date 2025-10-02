package com.example.fooddeliveryapp.domain.repository

import com.example.fooddeliveryapp.data.local.model.PopularItem
import kotlinx.coroutines.flow.Flow

interface MealRepository {
    suspend fun getRandomMeals(): Result<List<PopularItem>>
    suspend fun searchMeals(query: String): Result<List<PopularItem>>
    suspend fun getMealsByCategory(category: String): Result<List<PopularItem>>
    suspend fun getMealsByArea(area: String): Result<List<PopularItem>>
    suspend fun getMealById(id: String): Result<PopularItem?>
}