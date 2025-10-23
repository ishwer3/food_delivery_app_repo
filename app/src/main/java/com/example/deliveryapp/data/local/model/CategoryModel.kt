package com.example.deliveryapp.data.local.model

import com.example.deliveryapp.R

enum class CategoryType { DRINKS, COFFEE, BURGERS, PIZZA, SALADS, DESSERTS , BEVERAGES, BREAKFAST}

data class CategoryModel(
    val id: String,
    val icon: Int,
    val title: String,
    val description: String,
    val categoryType: CategoryType
){
    companion object{
        fun getSampleCategories(): List<CategoryModel>{
            return listOf(
                CategoryModel(
                    id = "1",
                    icon = R.drawable.bevarages_icon,
                    title = "Dessert",
                    description = "Fresh juices and beverages",
                    categoryType = CategoryType.DESSERTS
                ),
                CategoryModel(
                    id = "2",
                    icon = R.drawable.coffee_icon, // ☕ Coffee / Tea
                    title = "Breakfast",
                    description = "Hot coffee and tea selections",
                    categoryType = CategoryType.BREAKFAST
                ),
                CategoryModel(
                    id = "3",
                    icon = R.drawable.burger_icon, // 🍔 Burger
                    title = "Burgers",
                    description = "Delicious fast food burgers",
                    categoryType = CategoryType.BURGERS
                ),
                CategoryModel(
                    id = "4",
                    icon = R.drawable.pizza_icon, // 🍕 Pizza
                    title = "Pizza",
                    description = "Cheesy and tasty pizzas",
                    categoryType = CategoryType.PIZZA
                ),
                CategoryModel(
                    id = "5",
                    icon = R.drawable.salad_icon, // 🥗 Salad
                    title = "Salads",
                    description = "Healthy fresh salads",
                    categoryType = CategoryType.SALADS
                ),
                CategoryModel(
                    id = "7",
                    icon = R.drawable.soft_drink_icon, // 🥤 Water Bottle
                    title = "Beverages",
                    description = "Packaged water and soft drinks",
                    categoryType = CategoryType.BEVERAGES

                )
            )
        }
    }
}
