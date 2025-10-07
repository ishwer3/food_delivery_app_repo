package com.example.deliveryapp.data.repository

import com.example.deliveryapp.data.local.model.PopularItem
import com.example.deliveryapp.data.mapper.toPopularItem
import com.example.deliveryapp.data.mapper.toPopularItems
import com.example.deliveryapp.data.remote.api.MealApi
import com.example.deliveryapp.domain.repository.MealRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MealRepositoryImpl @Inject constructor(
    private val mealApi: MealApi
) : MealRepository {

    override suspend fun getRandomMeals(): Result<List<PopularItem>> {
        return try {
            val response = mealApi.getRandomMeals()
            val meals = response.meals?.toPopularItems() ?: emptyList()
            Result.success(meals)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun searchMeals(query: String): Result<List<PopularItem>> {
        return try {
            val response = mealApi.searchMeals(query)
            val meals = response.meals?.toPopularItems() ?: emptyList()
            Result.success(meals)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getMealsByCategory(category: String): Result<List<PopularItem>> {
        return try {
            val response = mealApi.getMealsByCategory(category)
            val meals = response.meals?.toPopularItems() ?: emptyList()
            Result.success(meals)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getMealsByArea(area: String): Result<List<PopularItem>> {
        return try {
            val response = mealApi.getMealsByArea(area)
            val meals = response.meals?.toPopularItems() ?: emptyList()
            Result.success(meals)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getMealById(id: String): Result<PopularItem?> {
        return try {
            val response = mealApi.getMealById(id)
            val meal = response.meals?.firstOrNull()?.toPopularItem()
            Result.success(meal)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}