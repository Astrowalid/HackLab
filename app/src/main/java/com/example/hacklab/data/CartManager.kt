package com.example.hacklab.data

import android.content.Context
import androidx.compose.runtime.mutableStateListOf
import com.example.hacklab.data.database.AppDatabase
import com.example.hacklab.data.repository.CartRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

object CartManager {
    private var repository: CartRepository? = null
    private val scope = CoroutineScope(Dispatchers.IO)

    val _cartItems = mutableStateListOf<CartItem>()
    val cartItems: List<CartItem> get() = _cartItems

    fun initialize(context: Context) {
        if (repository == null) {
            val database = AppDatabase.getDatabase(context)
            repository = CartRepository(database.cartDao())

            scope.launch {
                repository?.allCartItems?.collect { dbItems ->
                    _cartItems.clear()
                    _cartItems.addAll(dbItems.map { it.toCartItem() })
                }
            }
        }
    }

    fun addToCart(product: Product) {
        println("ADD TO CART CALLED - Product: ${product.name}, ID: ${product.id}")

        scope.launch {
            repository?.addToCart(product.toProductCart())

            val existingItem = _cartItems.find { it.id == product.id }
            if (existingItem != null) {
                val index = _cartItems.indexOf(existingItem)
                _cartItems[index] = existingItem.copy(quantity = existingItem.quantity + 1)
                println("🛒 Product quantity increased: ${existingItem.name}")
            } else {
                _cartItems.add(product.toCartItem())
                println("🛒 New product added: ${product.name}")
            }
        }
    }

    fun removeFromCart(itemId: Int) {
        scope.launch {
            repository?.removeFromCart(itemId)
            _cartItems.removeAll { it.id == itemId }
        }
    }

    fun updateQuantity(itemId: Int, quantity: Int) {
        scope.launch {
            repository?.updateQuantity(itemId, quantity)

            val item = _cartItems.find { it.id == itemId }
            if (item != null) {
                if (quantity > 0) {
                    val index = _cartItems.indexOf(item)
                    _cartItems[index] = item.copy(quantity = quantity)
                } else {
                    _cartItems.remove(item)
                }
            }
        }
    }

    fun getCartItemsCount(): Int {
        return _cartItems.sumOf { it.quantity }
    }

    fun clearCart() {
        scope.launch {
            repository?.clearCart()
            _cartItems.clear()
        }
    }
}

fun Product.toProductCart(): ProductCart {
    return ProductCart(
        productId = this.id,
        name = this.name,
        description = this.description,
        price = this.price,
        imageResId = this.imageResId,
        imageUrl = this.imageUrl,
        quantity = 1
    )
}

fun Product.toCartItem(): CartItem {
    return CartItem(
        id = this.id,
        name = this.name,
        description = this.description,
        price = this.price,
        imageResId = this.imageResId,
        imageUrl = this.imageUrl,
        quantity = 1
    )
}

fun ProductCart.toCartItem(): CartItem {
    return CartItem(
        id = this.productId,
        name = this.name,
        description = this.description,
        price = this.price,
        imageResId = this.imageResId,
        imageUrl = this.imageUrl,
        quantity = this.quantity
    )
}