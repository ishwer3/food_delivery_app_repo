package com.example.deliveryapp.data.repository

import com.example.deliveryapp.data.local.dao.CartDao
import com.example.deliveryapp.data.local.entity.toCartItem
import com.example.deliveryapp.data.local.entity.toEntity
import com.example.deliveryapp.data.local.model.CartItem
import com.example.deliveryapp.data.local.model.PopularItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CartRepository @Inject constructor(
    private val cartDao: CartDao
) {
    fun getAllCartItems(): Flow<List<CartItem>> {
        return cartDao.getAllCartItems().map { entities ->
            entities.map { it.toCartItem() }
        }
    }

    suspend fun addToCart(item: PopularItem, quantity: Int) {
        val existingItem = cartDao.getCartItemById(item.id)

        if (existingItem != null) {
            // Item already exists, update quantity
            val newQuantity = existingItem.quantity + quantity
            cartDao.updateQuantity(item.id, newQuantity)
        } else {
            // New item, insert it
            val cartItem = CartItem(popularItem = item, quantity = quantity)
            cartDao.insertCartItem(cartItem.toEntity())
        }
    }

    suspend fun removeFromCart(cartItem: CartItem) {
        cartDao.deleteCartItemById(cartItem.popularItem.id)
    }
    suspend fun updateQuantity(cartItem: CartItem, newQuantity: Int) {
        if (newQuantity <= 0) {
            removeFromCart(cartItem)
        } else {
            cartDao.updateQuantity(cartItem.popularItem.id, newQuantity)
        }
    }

    suspend fun clearCart() {
        cartDao.clearCart()
    }

    fun getTotalPrice(): Flow<Double> {
        return cartDao.getTotalPrice().map { it ?: 0.0 }
    }

    fun getItemCount(): Flow<Int> {
        return cartDao.getItemCount().map { it ?: 0 }
    }

    suspend fun isItemInCart(itemId: String): Boolean {
        return cartDao.isItemInCart(itemId)
    }

    suspend fun getCartItemById(itemId: String): CartItem? {
        return cartDao.getCartItemById(itemId)?.toCartItem()
    }

    fun getDistinctItemCount(): Flow<Int> {
        return cartDao.getDistinctItemCount()
    }
}