package com.example.fooddeliveryapp.domain.model

data class FoodItem(
    val id: String,
    val name: String,
    val description: String,
    val price: Double,
    val imageUrl: String,
    val category: FoodCategory,
    val rating: Double = 0.0,
    val isVegetarian: Boolean = false,
    val prepTime: Int = 0, // in minutes
    val calories: Int = 0
)

data class FoodCategory(
    val id: String,
    val name: String,
    val icon: String,
    val color: String = "#FF9800"
)

data class CartItem(
    val foodItem: FoodItem,
    val quantity: Int,
    val customizations: List<String> = emptyList()
) {
    val totalPrice: Double
        get() = foodItem.price * quantity
}

data class Order(
    val id: String,
    val items: List<CartItem>,
    val totalAmount: Double,
    val status: OrderStatus,
    val deliveryAddress: String,
    val estimatedDeliveryTime: String,
    val createdAt: String
)

enum class OrderStatus {
    PENDING,
    CONFIRMED,
    PREPARING,
    OUT_FOR_DELIVERY,
    DELIVERED,
    CANCELLED
}