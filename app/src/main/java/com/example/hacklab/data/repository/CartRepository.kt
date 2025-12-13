package com.example.hacklab.data.repository

import com.example.hacklab.data.ProductCart
import com.example.hacklab.data.dao.CartDao
import kotlinx.coroutines.flow.Flow

class CartRepository(private val cartDao: CartDao) {

    val allCartItems: Flow<List<ProductCart>> = cartDao.getAllCartItems()

    suspend fun addToCart(item: ProductCart) {
        val existingItem = cartDao.getCartItemById(item.productId)
        if (existingItem != null) {
            cartDao.updateQuantity(item.productId, existingItem.quantity + 1)
        } else {
            cartDao.insertCartItem(item)
        }
    }

    suspend fun updateQuantity(productId: Int, quantity: Int) {
        if (quantity > 0) {
            cartDao.updateQuantity(productId, quantity)
        } else {
            cartDao.deleteCartItemById(productId)
        }
    }

    suspend fun removeFromCart(productId: Int) {
        cartDao.deleteCartItemById(productId)
    }

    suspend fun clearCart() {
        cartDao.clearCart()
    }

    suspend fun getTotalQuantity(): Int {
        return cartDao.getTotalQuantity() ?: 0
    }

    suspend fun getTotalPrice(): Double {
        return cartDao.getTotalPrice() ?: 0.0
    }

    suspend fun getCartItemById(productId: Int): ProductCart? {
        return cartDao.getCartItemById(productId)
    }
}