package com.example.hacklab.data.dao

import androidx.room.*
import com.example.hacklab.data.FavoriteProduct
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavorite(product: FavoriteProduct)

    @Query("SELECT * FROM favorite_products")
    fun getAllFavorites(): Flow<List<FavoriteProduct>>

    @Query("SELECT * FROM favorite_products WHERE productId = :productId")
    suspend fun getFavoriteById(productId: Int): FavoriteProduct?

    @Query("SELECT EXISTS(SELECT 1 FROM favorite_products WHERE productId = :productId)")
    suspend fun isFavorite(productId: Int): Boolean

    @Delete
    suspend fun deleteFavorite(product: FavoriteProduct)

    @Query("DELETE FROM favorite_products WHERE productId = :productId")
    suspend fun deleteFavoriteById(productId: Int)

    @Query("DELETE FROM favorite_products")
    suspend fun deleteAllFavorites()
}