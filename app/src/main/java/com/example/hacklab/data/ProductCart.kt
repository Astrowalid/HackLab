package com.example.hacklab.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "product_cart")
data class ProductCart(
    @PrimaryKey
    val productId: Int,
    val name: String,
    val description: String,
    val price: Double,
    val imageResId: Int,
    val imageUrl: String = "",
    val quantity: Int = 1
)