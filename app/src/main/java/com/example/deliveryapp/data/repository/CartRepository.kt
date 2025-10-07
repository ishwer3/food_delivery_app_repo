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

/**
 * Repository layer for cart operations
 * Abstracts the data source (Room Database) from the ViewModel
 * All operations are suspend functions for coroutine support
 */
@Singleton
class CartRepository @Inject constructor(
    private val cartDao: CartDao
) {

    /**
     * Get all cart items as Flow for reactive updates
     * Converts CartItemEntity to CartItem for UI layer
     */
    fun getAllCartItems(): Flow<List<CartItem>> {
        return cartDao.getAllCartItems().map { entities ->
            entities.map { it.toCartItem() }
        }
    }

    /**
     * Add an item to cart or update quantity if already exists
     * @param item PopularItem to add
     * @param quantity Quantity to add
     */
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

    /**
     * Remove an item from cart
     * @param cartItem CartItem to remove
     */
    suspend fun removeFromCart(cartItem: CartItem) {
        cartDao.deleteCartItemById(cartItem.popularItem.id)
    }

    /**
     * Update quantity of a cart item
     * If quantity is 0 or less, removes the item from cart
     * @param cartItem CartItem to update
     * @param newQuantity New quantity
     */
    suspend fun updateQuantity(cartItem: CartItem, newQuantity: Int) {
        if (newQuantity <= 0) {
            removeFromCart(cartItem)
        } else {
            cartDao.updateQuantity(cartItem.popularItem.id, newQuantity)
        }
    }

    /**
     * Clear all items from cart
     */
    suspend fun clearCart() {
        cartDao.clearCart()
    }

    /**
     * Get total price of all items in cart
     * Returns 0.0 if cart is empty
     */
    fun getTotalPrice(): Flow<Double> {
        return cartDao.getTotalPrice().map { it ?: 0.0 }
    }

    /**
     * Get total count of items in cart (sum of quantities)
     * Returns 0 if cart is empty
     */
    fun getItemCount(): Flow<Int> {
        return cartDao.getItemCount().map { it ?: 0 }
    }

    /**
     * Check if an item exists in the cart
     * @param itemId ID of the item to check
     * @return true if item is in cart, false otherwise
     */
    suspend fun isItemInCart(itemId: String): Boolean {
        return cartDao.isItemInCart(itemId)
    }

    /**
     * Get a specific cart item by its ID
     * @param itemId ID of the item
     * @return CartItem if found, null otherwise
     */
    suspend fun getCartItemById(itemId: String): CartItem? {
        return cartDao.getCartItemById(itemId)?.toCartItem()
    }

    /**
     * Get count of distinct items in cart
     * (different from getItemCount which sums quantities)
     */
    fun getDistinctItemCount(): Flow<Int> {
        return cartDao.getDistinctItemCount()
    }
}