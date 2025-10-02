package com.example.fooddeliveryapp.presentation.state

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fooddeliveryapp.data.local.model.CartItem
import com.example.fooddeliveryapp.data.local.model.PopularItem
import com.example.fooddeliveryapp.data.repository.CartRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val cartRepository: CartRepository
) : ViewModel() {
    private val _cartItems = cartRepository.getAllCartItems()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Eagerly,
            initialValue = emptyList()
        )
    val cartItems: StateFlow<List<CartItem>> = _cartItems

    private val _totalPrice = cartRepository.getTotalPrice()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Eagerly,
            initialValue = 0.0
        )
    val totalPrice: StateFlow<Double> = _totalPrice
    private val _itemCount = cartRepository.getItemCount()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Eagerly,
            initialValue = 0
        )
    val itemCount: StateFlow<Int> = _itemCount

    fun addToCart(item: PopularItem, quantity: Int = 1) {
        viewModelScope.launch {
            cartRepository.addToCart(item, quantity)
        }
    }
    fun removeFromCart(cartItem: CartItem) {
        viewModelScope.launch {
            cartRepository.removeFromCart(cartItem)
        }
    }
    fun updateQuantity(cartItem: CartItem, newQuantity: Int) {
        viewModelScope.launch {
            cartRepository.updateQuantity(cartItem, newQuantity)
        }
    }

    fun clearCart() {
        viewModelScope.launch {
            cartRepository.clearCart()
        }
    }

    suspend fun isItemInCart(itemId: String): Boolean {
        return cartRepository.isItemInCart(itemId)
    }
}