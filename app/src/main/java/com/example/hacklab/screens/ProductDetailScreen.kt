package com.example.hacklab.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ShoppingCart
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
import com.example.hacklab.data.ProductRepository


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductDetailScreen(
    productId: Int? = 1,
    onBackClick: () -> Unit = {},
    onAddToCart: () -> Unit = {}
) {
    // ✅ Récupérer le produit par ID du repository
    val product = ProductRepository.getProductById(productId ?: 1)

    if (product == null) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFF0A0A12)),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                "Product not found",
                color = Color.White,
                fontSize = 20.sp
            )
        }
        return
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF0A0A12))
    ) {
        // ===== TOP APP BAR =====
        TopAppBar(
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
                .background(Color(0xFF0A0A12)),
            title = {},
            navigationIcon = {
                IconButton(onClick = onBackClick) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Back",
                        tint = Color.White,
                        modifier = Modifier.size(24.dp)
                    )
                }
            },
            actions = {
                IconButton(onClick = { }) {
                    Icon(
                        imageVector = Icons.Default.ShoppingCart,
                        contentDescription = "Cart",
                        tint = Color.White,
                        modifier = Modifier.size(24.dp)
                    )
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = Color(0xFF0A0A12)
            )
        )

        // ===== CONTENU SCROLLABLE =====
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .weight(1f)
        ) {
            // Image du produit
            item {
                ProductDetailImage(
                    imageResId = product.imageResId,
                    productName = product.name
                )
            }

            // Infos du produit
            item {
                ProductDetailInfo(
                    name = product.name,
                    description = product.description,
                    price = product.price
                )
            }

            // Spécifications
            item {
                if (product.specifications.isNotEmpty()) {
                    SpecificationsSection(
                        specifications = product.specifications
                    )
                }
            }

            // Safety Disclaimer
            item {
                if (product.safetyDisclaimer.isNotEmpty()) {
                    SafetyDisclaimerSection(
                        disclaimer = product.safetyDisclaimer
                    )
                }
            }

            // Espacement pour le bottom nav
            item {
                Spacer(modifier = Modifier.height(100.dp))
            }
        }

        // ===== BOUTON "ADD TO CART" (Fixed at bottom) =====
        AddToCartButton(
            price = product.price,
            onAddToCart = onAddToCart
        )
    }
}

@Composable
fun ProductDetailImage(
    imageResId: Int,
    productName: String
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp)
            .padding(16.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF323236)
        )
    ) {
        Image(
            painter = painterResource(id = imageResId),
            contentDescription = productName,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Fit
        )
    }
}

@Composable
fun ProductDetailInfo(
    name: String,
    description: String,
    price: Double
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = name,
            color = Color.White,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )

        Text(
            text = description,
            color = Color(0xFFCCCCCC),
            fontSize = 14.sp,
            lineHeight = 20.sp
        )
    }
}

@Composable
fun SpecificationsSection(
    specifications: Map<String, String>
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Specifications",
            color = Color(0xFFDE0000),
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold
        )

        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            specifications.forEach { (key, value) ->
                SpecificationRow(key = key, value = value)
            }
        }
    }
}

@Composable
fun SpecificationRow(
    key: String,
    value: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = key,
            color = Color(0xFF999999),
            fontSize = 13.sp
        )

        Text(
            text = value,
            color = Color.White,
            fontSize = 13.sp,
            fontWeight = FontWeight.SemiBold
        )
    }

    Divider(
        color = Color(0xFF242847),
        thickness = 1.dp,
        modifier = Modifier.padding(vertical = 4.dp)
    )
}

@Composable
fun SafetyDisclaimerSection(
    disclaimer: String
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = "Safety Disclaimer",
            color = Color(0xFFFFC107),
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold
        )

        Text(
            text = disclaimer,
            color = Color(0xFFFFC107),
            fontSize = 13.sp,
            lineHeight = 18.sp
        )
    }
}

@Composable
fun AddToCartButton(
    price: Double,
    onAddToCart: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFF0A0A12))
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = "Price",
            color = Color(0xFF999999),
            fontSize = 12.sp
        )

        Text(
            text = "$${price}",
            color = Color.White,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )

        Button(
            onClick = onAddToCart,
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFDE0000),
                contentColor = Color.White
            ),
            shape = RoundedCornerShape(8.dp)
        ) {
            Text(
                "Add to Cart",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}