package com.example.hacklab.data.api

import retrofit2.http.GET
import retrofit2.http.Path

interface HackLabApiService {

    @GET("products")
    suspend fun getProducts(): List<ApiProduct>

    @GET("products/{id}")
    suspend fun getProductById(@Path("id") id: Int): ApiProduct

    @GET("categories")
    suspend fun getCategories(): List<ApiCategory>
}