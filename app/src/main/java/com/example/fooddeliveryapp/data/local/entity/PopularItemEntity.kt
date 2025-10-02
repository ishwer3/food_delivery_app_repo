package com.example.fooddeliveryapp.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.fooddeliveryapp.data.local.model.PopularItem

/**
 * Room Entity representing a PopularItem in the database
 * This is embedded within CartItemEntity to store complete product information
 */
@Entity(tableName = "popular_items")
data class PopularItemEntity(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: String,

    @ColumnInfo(name = "title")
    val title: String,

    @ColumnInfo(name = "description")
    val description: String,

    @ColumnInfo(name = "price")
    val price: Double,

    @ColumnInfo(name = "hotel_name")
    val hotelName: String,

    @ColumnInfo(name = "distance")
    val distance: String,

    @ColumnInfo(name = "category")
    val category: String,

    @ColumnInfo(name = "image_res")
    val imageRes: Int,

    @ColumnInfo(name = "image_url")
    val imageUrl: String?,

    @ColumnInfo(name = "rating")
    val rating: Float,

    @ColumnInfo(name = "is_vegetarian")
    val isVegetarian: Boolean
)

/**
 * Extension function to convert PopularItemEntity to PopularItem
 */
fun PopularItemEntity.toPopularItem(): PopularItem {
    return PopularItem(
        id = id,
        title = title,
        description = description,
        price = price,
        hotelName = hotelName,
        distance = distance,
        category = category,
        imageRes = imageRes,
        imageUrl = imageUrl,
        rating = rating,
        isVegetarian = isVegetarian
    )
}

/**
 * Extension function to convert PopularItem to PopularItemEntity
 */
fun PopularItem.toEntity(): PopularItemEntity {
    return PopularItemEntity(
        id = id,
        title = title,
        description = description,
        price = price,
        hotelName = hotelName,
        distance = distance,
        category = category,
        imageRes = imageRes,
        imageUrl = imageUrl,
        rating = rating,
        isVegetarian = isVegetarian
    )
}