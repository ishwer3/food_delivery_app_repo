package com.example.deliveryapp.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.deliveryapp.data.local.model.CartItem

@Entity(tableName = "cart_items")
data class CartItemEntity(
    @PrimaryKey
    @ColumnInfo(name = "item_id")
    val itemId: String, // Uses the popularItem's id as primary key

    @Embedded
    val popularItem: PopularItemEntity,

    @ColumnInfo(name = "quantity")
    val quantity: Int,

    @ColumnInfo(name = "added_at")
    val addedAt: Long = System.currentTimeMillis() // Timestamp for ordering
)

/**
 * Extension function to convert CartItemEntity to CartItem
 */
fun CartItemEntity.toCartItem(): CartItem {
    return CartItem(
        popularItem = popularItem.toPopularItem(),
        quantity = quantity
    )
}

/**
 * Extension function to convert CartItem to CartItemEntity
 */
fun CartItem.toEntity(): CartItemEntity {
    return CartItemEntity(
        itemId = popularItem.id,
        popularItem = popularItem.toEntity(),
        quantity = quantity
    )
}