package com.example.deliveryapp.data.mapper

object CategoryMapper {
    fun mapUIToAPICategory(uiCategory: String): String {
        return when (uiCategory.lowercase()) {
            "burgers" -> "Beef" // Most burgers are beef
            "pizza" -> "Miscellaneous"
            "salads" -> "Vegetarian"
            "desserts" -> "Dessert"
            else -> uiCategory
        }
    }
}