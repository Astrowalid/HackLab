package com.example.hacklab.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_products")
data class FavoriteProduct(
    @PrimaryKey
    val productId: Int,
    val name: String,
    val author: String,
    val price: Double,
    val category: String,
    val imageResId: Int
)