package com.example.hacklab.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.hacklab.R
import com.example.hacklab.viewmodel.ProductViewModel
import coil.compose.AsyncImage


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductListScreen(
    onProductClick: (Int) -> Unit = {},
    viewModel: ProductViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
    var selectedCategory: String? by remember { mutableStateOf(null) }
    var searchQuery by remember { mutableStateOf("") }

    val allProducts = viewModel.products
    val isLoading = viewModel.isLoading
    val errorMessage = viewModel.errorMessage

    val filteredProducts = allProducts.filter { product ->
        (selectedCategory == null || product.category == selectedCategory) &&
                (searchQuery.isEmpty() || product.name.contains(searchQuery, ignoreCase = true))
    }

    val categories = listOf("All", "Exploits", "Recon", "Post-Exploitation")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF0A0A12))
    ) {
        TopAppBar(
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
                .background(Color(0xFF0A0A12)),
            title = {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.hacklab_white),
                        contentDescription = "HackLab Logo",
                        modifier = Modifier.size(50.dp),
                        contentScale = ContentScale.Fit
                    )

                    IconButton(onClick = { }) {
                        Icon(
                            imageVector = Icons.Default.ShoppingCart,
                            contentDescription = "Cart",
                            tint = Color.White,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = Color(0xFF0A0A12)
            )
        )

        SearchBar(
            query = searchQuery,
            onQueryChange = { searchQuery = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp)
        )

        Spacer(modifier = Modifier.height(8.dp))

        CategoriesRow(
            categories = categories,
            selectedCategory = selectedCategory,
            onCategorySelected = {
                selectedCategory = if (it == "All") null else it
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        if (isLoading) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(color = Color(0xFFDE0000))
            }
        }
        else if (errorMessage != null) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Error: $errorMessage",
                    color = Color.White,
                    fontSize = 16.sp
                )
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = { viewModel.loadProducts() },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFDE0000)
                    )
                ) {
                    Text("Retry")
                }
            }
        }
        else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(filteredProducts) { product ->
                    ProductCard(
                        product = product,
                        onClick = { onProductClick(product.id) }
                    )
                }

                item {
                    Spacer(modifier = Modifier.height(80.dp))
                }
            }
        }
    }
}

@Composable
fun SearchBar(
    query: String,
    onQueryChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    OutlinedTextField(
        value = query,
        onValueChange = onQueryChange,
        placeholder = { Text("Search for tools", color = Color(0xFF999999)) },
        leadingIcon = {
            Icon(
                Icons.Default.Search,
                contentDescription = "Search",
                tint = Color(0xFF999999)
            )
        },
        modifier = modifier
            .fillMaxWidth()
            .height(50.dp),
        singleLine = true,
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = Color(0xFFDE0000),
            unfocusedBorderColor = Color(0xFF242847),
            focusedTextColor = Color.White,
            unfocusedTextColor = Color.White,
            cursorColor = Color(0xFFDE0000)
        ),
        shape = RoundedCornerShape(8.dp)
    )
}

@Composable
fun CategoriesRow(
    categories: List<String>,
    selectedCategory: String?,
    onCategorySelected: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .horizontalScroll(rememberScrollState()),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        categories.forEach { category ->
            val isSelected = (category == "All" && selectedCategory == null) ||
                    (category != "All" && category == selectedCategory)

            FilterChip(
                selected = isSelected,
                onClick = { onCategorySelected(category) },
                label = {
                    Text(
                        category,
                        color = if (isSelected) Color.White else Color(0xFF999999),
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                    )
                },
                colors = FilterChipDefaults.filterChipColors(
                    selectedContainerColor = Color(0xFFDE0000),
                    containerColor = Color(0xFF242847)
                ),
                border = FilterChipDefaults.filterChipBorder(
                    selected = isSelected,
                    enabled = true,
                    selectedBorderColor = Color(0xFFDE0000),
                    borderColor = Color(0xFF242847)
                )
            )
        }
    }
}

@Composable
fun ProductCard(
    product: com.example.hacklab.data.Product,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .height(110.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF242847)
        ),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Card(
                modifier = Modifier.size(150.dp),
                shape = RoundedCornerShape(8.dp)
            ) {
                if (product.imageUrl.isNotEmpty()) {
                    AsyncImage(
                        model = product.imageUrl,
                        contentDescription = product.name,
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop,
                        placeholder = painterResource(id = R.drawable.product_1),
                        error = painterResource(id = R.drawable.product_1)
                    )
                } else if (product.imageResId != 0) {
                    Image(
                        painter = painterResource(id = product.imageResId),
                        contentDescription = product.name,
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                } else {
                    Image(
                        painter = painterResource(id = R.drawable.product_1),
                        contentDescription = product.name,
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "$${product.price}",
                    color = Color(0xFF999999),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = product.name,
                    color = Color.White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    maxLines = 2
                )

                Text(
                    text = product.author,
                    color = Color(0xFF999999),
                    fontSize = 12.sp
                )
            }
        }
    }
}