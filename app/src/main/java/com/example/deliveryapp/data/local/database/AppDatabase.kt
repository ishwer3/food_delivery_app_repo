package com.example.deliveryapp.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.deliveryapp.data.local.dao.CartDao
import com.example.deliveryapp.data.local.entity.CartItemEntity

/**
 * Room Database for the Food Delivery App
 * Version 1: Initial database with cart functionality
 *
 * Entities:
 * - CartItemEntity: Stores cart items with embedded PopularItemEntity
 */
@Database(
    entities = [CartItemEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    /**
     * Provides access to CartDao for cart operations
     */
    abstract fun cartDao(): CartDao

    companion object {
        const val DATABASE_NAME = "food_delivery_database"
    }
}