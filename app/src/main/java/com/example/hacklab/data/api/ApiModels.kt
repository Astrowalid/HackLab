package com.example.hacklab.data.api

import com.google.gson.annotations.SerializedName

data class ApiProduct(
    @SerializedName("id")
    val id: Int,

    @SerializedName("name")
    val name: String,

    @SerializedName("author")
    val author: String,

    @SerializedName("price")
    val price: Double,

    @SerializedName("category")
    val category: String,

    @SerializedName("image")
    val image: String,

    @SerializedName("description")
    val description: String,

    @SerializedName("specifications")
    val specifications: Map<String, String>,

    @SerializedName("safetyDisclaimer")
    val safetyDisclaimer: String
)

data class ApiCategory(
    @SerializedName("id")
    val id: Int,

    @SerializedName("name")
    val name: String
)