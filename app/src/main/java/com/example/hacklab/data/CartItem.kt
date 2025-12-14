package com.example.hacklab.data

data class CartItem(
    val id: Int,
    val name: String,
    val description: String,
    val price: Double,
    val imageResId: Int,
    val imageUrl: String = "",
    val quantity: Int = 1
)