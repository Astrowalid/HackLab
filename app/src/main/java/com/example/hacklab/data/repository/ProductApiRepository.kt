package com.example.hacklab.data.repository

import com.example.hacklab.data.Product
import com.example.hacklab.data.api.ApiProduct
import com.example.hacklab.data.api.RetrofitInstance

class ProductApiRepository {

    private val api = RetrofitInstance.api

    suspend fun fetchProducts(): Result<List<Product>> {
        return try {
            val apiProducts = api.getProducts()
            val products = apiProducts.map { it.toProduct() }
            Result.success(products)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun fetchProductById(id: Int): Result<Product> {
        return try {
            val apiProduct = api.getProductById(id)
            Result.success(apiProduct.toProduct())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    private fun ApiProduct.toProduct(): Product {
        return Product(
            id = this.id,
            name = this.name,
            author = this.author,
            price = this.price,
            category = this.category,
            imageResId = 0,
            imageUrl = this.image,
            description = this.description,
            specifications = this.specifications,
            safetyDisclaimer = this.safetyDisclaimer
        )
    }
}