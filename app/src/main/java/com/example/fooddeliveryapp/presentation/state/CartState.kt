package com.example.fooddeliveryapp.presentation.state

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.example.fooddeliveryapp.data.local.model.CartItem
import com.example.fooddeliveryapp.data.local.model.PopularItem

class CartViewModel : ViewModel() {
    private val _cartItems = mutableStateListOf<CartItem>()
    val cartItems: List<CartItem> = _cartItems

    val totalPrice: Double
        get() = _cartItems.sumOf { it.totalPrice }

    val itemCount: Int
        get() = _cartItems.sumOf { it.quantity }

    fun addToCart(item: PopularItem, quantity: Int) {
        val existingCartItem = _cartItems.find { it.popularItem.id == item.id }

        if (existingCartItem != null) {
            val newQuantity = existingCartItem.quantity + quantity
            val index = _cartItems.indexOf(existingCartItem)
            _cartItems[index] = existingCartItem.copy(quantity = newQuantity)
        } else {
            _cartItems.add(CartItem(item, quantity))
        }
    }

    fun removeFromCart(cartItem: CartItem) {
        _cartItems.remove(cartItem)
    }

    fun updateQuantity(cartItem: CartItem, newQuantity: Int) {
        if (newQuantity <= 0) {
            removeFromCart(cartItem)
        } else {
            val index = _cartItems.indexOf(cartItem)
            if (index != -1) {
                _cartItems[index] = cartItem.copy(quantity = newQuantity)
            }
        }
    }

    fun clearCart() {
        _cartItems.clear()
    }
}