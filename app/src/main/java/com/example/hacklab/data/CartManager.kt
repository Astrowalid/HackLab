package com.example.hacklab.data

import androidx.compose.runtime.mutableStateListOf

object CartManager {
    public val _cartItems = mutableStateListOf<CartItem>() // make it private
    val cartItems: List<CartItem> get() = _cartItems

    fun addToCart(product: Product) {
        println("ADD TO CART CALLED - Product: ${product.name}, ID: ${product.id}")

        val existingItem = _cartItems.find { it.id == product.id }
        if (existingItem != null) {
            val index = _cartItems.indexOf(existingItem)
            _cartItems[index] = existingItem.copy(quantity = existingItem.quantity + 1)
            println("🛒 Product quantity increased: ${existingItem.name}, New quantity: ${_cartItems[index].quantity}")
        } else {
            _cartItems.add(
                CartItem(
                    id = product.id,
                    name = product.name,
                    description = product.description,
                    price = product.price,
                    imageResId = product.imageResId,
                    quantity = 1
                )
            )
            println("🛒 New product added: ${product.name}")
        }

        println("🛒 Total cart items: ${_cartItems.size}")
        _cartItems.forEach {
            println("   - ${it.name} (Qty: ${it.quantity})")
        }
    }

    fun removeFromCart(itemId: Int) {
        _cartItems.removeAll { it.id == itemId }
    }

    fun getCartItemsCount(): Int {
        return _cartItems.sumOf { it.quantity }
    }

    fun clearCart() {
        _cartItems.clear()
    }
}