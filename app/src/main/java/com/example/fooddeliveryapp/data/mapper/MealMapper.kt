package com.example.fooddeliveryapp.data.mapper

import com.example.fooddeliveryapp.data.local.model.PopularItem
import com.example.fooddeliveryapp.data.remote.dto.MealDto
import kotlin.random.Random

fun MealDto.toPopularItem(): PopularItem {
    val description = strInstructions?.take(100) ?: "Delicious meal prepared with fresh ingredients"
    val price = String.format("%.2f", Random.nextDouble(15.0, 50.0)).toDouble()
    val hotelName = "${strArea ?: "International"} Kitchen"
    val distance = String.format("%.1f km", Random.nextDouble(0.5, 5.0))
    val rating = String.format("%.1f", Random.nextFloat() * 1.5f + 3.5f).toFloat() // Rating between 3.5 and 5.0
    val isVegetarian = strCategory?.lowercase()?.contains("vegetarian") == true ||
                      strCategory?.lowercase()?.contains("vegan") == true

    return PopularItem(
        id = idMeal,
        title = strMeal,
        description = description,
        price = price,
        hotelName = hotelName,
        distance = distance,
        category = strCategory ?: "Unknown",
        imageUrl = strMealThumb, // Add imageUrl field to PopularItem
        rating = rating,
        isVegetarian = isVegetarian
    )
}

fun List<MealDto>.toPopularItems(): List<PopularItem> {
    return this.map { it.toPopularItem() }
}