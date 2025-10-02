package com.example.fooddeliveryapp.data.local.model

import com.example.fooddeliveryapp.R

data class CategoryModel(
    val id: String,
    val icon: Int,
    val title: String,
    val description: String
){
    companion object{
        fun getSampleCategories(): List<CategoryModel>{
            return listOf(
                CategoryModel(
                    id = "1",
                    icon = R.drawable.bevarages_icon,
                    title = "Drinks",
                    description = "Fresh juices and beverages"
                ),
                CategoryModel(
                    id = "2",
                    icon = R.drawable.coffee_icon, // ☕ Coffee / Tea
                    title = "Coffee",
                    description = "Hot coffee and tea selections"
                ),
                CategoryModel(
                    id = "3",
                    icon = R.drawable.burger_icon, // 🍔 Burger
                    title = "Burgers",
                    description = "Delicious fast food burgers"
                ),
                CategoryModel(
                    id = "4",
                    icon = R.drawable.pizza_icon, // 🍕 Pizza
                    title = "Pizza",
                    description = "Cheesy and tasty pizzas"
                ),
                CategoryModel(
                    id = "5",
                    icon = R.drawable.salad_icon, // 🥗 Salad
                    title = "Salads",
                    description = "Healthy fresh salads"
                ),
                CategoryModel(
                    id = "6",
                    icon = R.drawable.icecream_icon, // 🍨 Ice cream / Dessert
                    title = "Desserts",
                    description = "Cakes and ice creams"
                ),
                CategoryModel(
                    id = "7",
                    icon = R.drawable.soft_drink_icon, // 🥤 Water Bottle
                    title = "Beverages",
                    description = "Packaged water and soft drinks"
                )
            )
        }
    }
}
