package com.example.deliveryapp.data.local.model

data class CartItem(
    val popularItem: PopularItem,
    val quantity: Int
) {
    val totalPrice: Double
        get() = popularItem.price * quantity
}