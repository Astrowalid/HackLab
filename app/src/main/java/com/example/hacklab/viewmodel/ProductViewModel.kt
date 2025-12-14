package com.example.hacklab.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hacklab.data.Product
import com.example.hacklab.data.repository.ProductApiRepository
import kotlinx.coroutines.launch

class ProductViewModel : ViewModel() {

    private val repository = ProductApiRepository()

    var products by mutableStateOf<List<Product>>(emptyList())
        private set

    var isLoading by mutableStateOf(false)
        private set

    var errorMessage by mutableStateOf<String?>(null)
        private set

    init {
        loadProducts()
    }

    fun loadProducts() {
        viewModelScope.launch {
            isLoading = true
            errorMessage = null

            repository.fetchProducts()
                .onSuccess { productList ->
                    products = productList
                    isLoading = false
                }
                .onFailure { error ->
                    errorMessage = error.message
                    isLoading = false
                }
        }
    }

    fun getProductById(id: Int): Product? {
        return products.find { it.id == id }
    }
}