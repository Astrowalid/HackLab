package com.example.hacklab.data.dao

import androidx.room.*
import com.example.hacklab.data.ProductCart
import kotlinx.coroutines.flow.Flow

@Dao
interface CartDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCartItem(item: ProductCart)

    @Query("SELECT * FROM product_cart")
    fun getAllCartItems(): Flow<List<ProductCart>>

    @Query("SELECT * FROM product_cart WHERE productId = :productId")
    suspend fun getCartItemById(productId: Int): ProductCart?

    @Query("SELECT SUM(quantity) FROM product_cart")
    suspend fun getTotalQuantity(): Int?

    @Query("SELECT SUM(price * quantity) FROM product_cart")
    suspend fun getTotalPrice(): Double?

    @Update
    suspend fun updateCartItem(item: ProductCart)

    @Query("UPDATE product_cart SET quantity = :quantity WHERE productId = :productId")
    suspend fun updateQuantity(productId: Int, quantity: Int)

    @Query("DELETE FROM product_cart WHERE productId = :productId")
    suspend fun deleteCartItemById(productId: Int)

    @Query("DELETE FROM product_cart")
    suspend fun clearCart()
}