package com.example.deliveryapp.data.local.model

import com.example.deliveryapp.R

data class PopularItem(
    val id: String,
    val title: String,
    val description: String,
    val price: Double,
    val hotelName: String,
    val distance: String,
    val category: String,
    val imageRes: Int = R.drawable.ic_launcher_foreground, // Placeholder image for static data
    val imageUrl: String? = null, // For API images
    val rating: Float = 4.5f,
    val isVegetarian: Boolean = false
) {
    companion object {
        fun getPopularItems(): List<PopularItem> {
            return listOf(
                // Pizzas
                PopularItem(
                    id = "1",
                    title = "Margherita Pizza",
                    description = "Classic pizza with fresh tomatoes, mozzarella and basil",
                    price = 12.99,
                    hotelName = "Mario's Pizza Corner",
                    distance = "0.8 km",
                    category = "Pizza",
                    rating = 4.7f,
                    isVegetarian = true
                ),
                PopularItem(
                    id = "2",
                    title = "Pepperoni Pizza",
                    description = "Spicy pepperoni with cheese and tomato sauce",
                    price = 15.99,
                    hotelName = "Tony's Pizzeria",
                    distance = "1.2 km",
                    category = "Pizza",
                    rating = 4.6f
                ),
                PopularItem(
                    id = "3",
                    title = "BBQ Chicken Pizza",
                    description = "Grilled chicken with BBQ sauce and red onions",
                    price = 18.99,
                    hotelName = "Pizza Palace",
                    distance = "2.1 km",
                    category = "Pizza",
                    rating = 4.8f
                ),

                // Burgers
                PopularItem(
                    id = "4",
                    title = "Classic Cheeseburger",
                    description = "Beef patty with cheese, lettuce, tomato and pickles",
                    price = 9.99,
                    hotelName = "Burger Junction",
                    distance = "0.5 km",
                    category = "Burgers",
                    rating = 4.5f
                ),
                PopularItem(
                    id = "5",
                    title = "Veggie Deluxe Burger",
                    description = "Plant-based patty with avocado and fresh vegetables",
                    price = 11.99,
                    hotelName = "Green Bites",
                    distance = "1.8 km",
                    category = "Burgers",
                    rating = 4.4f,
                    isVegetarian = true
                ),
                PopularItem(
                    id = "6",
                    title = "Chicken Crispy Burger",
                    description = "Crispy fried chicken with spicy mayo and coleslaw",
                    price = 10.99,
                    hotelName = "Crispy Corner",
                    distance = "1.0 km",
                    category = "Burgers",
                    rating = 4.6f
                ),

                // Beverages
                PopularItem(
                    id = "7",
                    title = "Fresh Orange Juice",
                    description = "Freshly squeezed orange juice with pulp",
                    price = 4.99,
                    hotelName = "Juice Bar",
                    distance = "0.3 km",
                    category = "Drinks",
                    rating = 4.3f,
                    isVegetarian = true
                ),
                PopularItem(
                    id = "8",
                    title = "Iced Coffee",
                    description = "Cold brew coffee with milk and ice",
                    price = 3.99,
                    hotelName = "Coffee Central",
                    distance = "0.7 km",
                    category = "Coffee",
                    rating = 4.5f,
                    isVegetarian = true
                ),
                PopularItem(
                    id = "9",
                    title = "Mango Smoothie",
                    description = "Thick mango smoothie with yogurt and honey",
                    price = 5.99,
                    hotelName = "Smoothie Station",
                    distance = "1.4 km",
                    category = "Drinks",
                    rating = 4.7f,
                    isVegetarian = true
                ),

                // Desserts
                PopularItem(
                    id = "10",
                    title = "Chocolate Brownie",
                    description = "Rich chocolate brownie with vanilla ice cream",
                    price = 6.99,
                    hotelName = "Sweet Treats",
                    distance = "1.6 km",
                    category = "Desserts",
                    rating = 4.8f,
                    isVegetarian = true
                ),
                PopularItem(
                    id = "11",
                    title = "Strawberry Cheesecake",
                    description = "Creamy cheesecake topped with fresh strawberries",
                    price = 7.99,
                    hotelName = "Bakery Bliss",
                    distance = "2.3 km",
                    category = "Desserts",
                    rating = 4.9f,
                    isVegetarian = true
                ),

                // Salads
                PopularItem(
                    id = "12",
                    title = "Caesar Salad",
                    description = "Crispy lettuce with parmesan, croutons and caesar dressing",
                    price = 8.99,
                    hotelName = "Fresh Garden",
                    distance = "0.9 km",
                    category = "Salads",
                    rating = 4.4f,
                    isVegetarian = true
                )
            )
        }
    }
}