package com.example.fooddeliveryapp.data.local.model

data class CartItem(
    val popularItem: PopularItem,
    val quantity: Int
) {
    val totalPrice: Double
        get() = popularItem.price * quantity
}