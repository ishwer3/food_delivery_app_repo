package com.example.deliveryapp.data.mapper

object CategoryMapper {
    fun mapUIToAPICategory(uiCategory: String): String {
        return when (uiCategory.lowercase()) {
            "burgers" -> "Beef" // Most burgers are beef
            "pizza" -> "Miscellaneous"
            "salads" -> "Vegetarian"
            "dessert" -> "Dessert"
            "desserts" -> "Dessert"
            "breakfast" -> "Breakfast"
            "beverages" -> "Dessert" // API doesn't have beverages, map to Dessert
            else -> uiCategory
        }
    }
}