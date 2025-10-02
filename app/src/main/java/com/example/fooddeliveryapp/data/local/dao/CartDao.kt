package com.example.fooddeliveryapp.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.fooddeliveryapp.data.local.entity.CartItemEntity
import kotlinx.coroutines.flow.Flow

/**
 * Data Access Object for cart operations
 * All database operations return Flow for reactive updates in the UI
 */
@Dao
interface CartDao {

    /**
     * Insert a cart item. If item already exists (based on primary key), replace it
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCartItem(cartItem: CartItemEntity)

    /**
     * Update an existing cart item
     */
    @Update
    suspend fun updateCartItem(cartItem: CartItemEntity)

    /**
     * Delete a specific cart item
     */
    @Delete
    suspend fun deleteCartItem(cartItem: CartItemEntity)

    /**
     * Delete a cart item by its ID
     */
    @Query("DELETE FROM cart_items WHERE item_id = :itemId")
    suspend fun deleteCartItemById(itemId: String)

    /**
     * Get all cart items as Flow for reactive updates
     * Ordered by added_at timestamp (newest first)
     */
    @Query("SELECT * FROM cart_items ORDER BY added_at DESC")
    fun getAllCartItems(): Flow<List<CartItemEntity>>

    /**
     * Get a specific cart item by ID
     */
    @Query("SELECT * FROM cart_items WHERE item_id = :itemId")
    suspend fun getCartItemById(itemId: String): CartItemEntity?

    /**
     * Clear all cart items
     */
    @Query("DELETE FROM cart_items")
    suspend fun clearCart()

    /**
     * Get total number of items in cart (sum of all quantities)
     */
    @Query("SELECT SUM(quantity) FROM cart_items")
    fun getItemCount(): Flow<Int?>

    /**
     * Get total price of all items in cart
     */
    @Query("SELECT SUM(quantity * price) FROM cart_items")
    fun getTotalPrice(): Flow<Double?>

    /**
     * Update quantity of a specific cart item
     */
    @Query("UPDATE cart_items SET quantity = :quantity WHERE item_id = :itemId")
    suspend fun updateQuantity(itemId: String, quantity: Int)

    /**
     * Check if an item exists in the cart
     */
    @Query("SELECT EXISTS(SELECT 1 FROM cart_items WHERE item_id = :itemId)")
    suspend fun isItemInCart(itemId: String): Boolean

    /**
     * Get the count of distinct items in cart
     */
    @Query("SELECT COUNT(*) FROM cart_items")
    fun getDistinctItemCount(): Flow<Int>
}