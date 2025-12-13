package com.example.hacklab.screens

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.hacklab.R
import com.example.hacklab.data.CartManager
import com.example.hacklab.data.ProductRepository
import com.example.hacklab.utils.NotificationHelper

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductDetailScreen(
    productId: Int?,
    onBackClick: () -> Unit,
    onCartClick: () -> Unit,
    navController: NavController
) {
    val context = LocalContext.current
    
    // Retrieve product details using the ID
    val product = productId?.let { ProductRepository.getProductById(it) }

    if (product == null) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFF0A0A12)),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Product not found",
                color = Color.White,
                fontSize = 18.sp
            )
        }
        return
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = { 
                    // Ajoute le produit au panier
                    CartManager.addToCart(product)

                    Toast.makeText(context, "Added to cart", Toast.LENGTH_SHORT).show()
                    
                    // Envoyer une notification locale
                    NotificationHelper.showNotification(
                        context,
                        "Produit ajouté !",
                        "Le produit ${product.name} a été ajouté à votre panier.",
                        productId
                    )
                },
                containerColor = Color(0xFFDE0000),
                contentColor = Color.White
            ) {
                Icon(Icons.Default.ShoppingCart, contentDescription = "Add to Cart")
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFF0A0A12))
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
        ) {
            // Image du produit
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
            ) {
                Image(
                    painter = painterResource(id = product.imageResId),
                    contentDescription = product.name,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
                
                // Bouton retour
                IconButton(
                    onClick = onBackClick,
                    modifier = Modifier
                        .padding(16.dp)
                        .align(Alignment.TopStart)
                        .background(Color.Black.copy(alpha = 0.5f), RoundedCornerShape(50))
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Back",
                        tint = Color.White
                    )
                }
            }

            Column(modifier = Modifier.padding(16.dp)) {
                // Titre et Prix
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Top
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = product.name,
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                        Text(
                            text = product.author,
                            fontSize = 14.sp,
                            color = Color.Gray
                        )
                    }
                    Text(
                        text = "$${product.price}",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFFDE0000)
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Note (Rating)
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = null,
                        tint = Color(0xFFFFD700),
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(text = "4.5", color = Color.Gray)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = "(120 reviews)", color = Color.Gray)
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Description
                Text(
                    text = "Description",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = product.description,
                    fontSize = 14.sp,
                    color = Color.Gray,
                    lineHeight = 20.sp
                )
                
                Spacer(modifier = Modifier.height(16.dp))
                
                // Specifications
                if (product.specifications.isNotEmpty()) {
                    Text(
                        text = "Specifications",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    product.specifications.forEach { (key, value) ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(text = key, color = Color.Gray, fontSize = 14.sp)
                            Text(text = value, color = Color.White, fontSize = 14.sp, fontWeight = FontWeight.SemiBold)
                        }
                    }
                }
                
                Spacer(modifier = Modifier.height(16.dp))
                
                // Disclaimer
                if (product.safetyDisclaimer.isNotEmpty()) {
                    Card(
                        colors = CardDefaults.cardColors(containerColor = Color(0xFF242847)),
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(12.dp)) {
                            Text(
                                text = "Disclaimer",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFFFF9800)
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = product.safetyDisclaimer,
                                fontSize = 12.sp,
                                color = Color.LightGray
                            )
                        }
                    }
                }
                
                // Padding pour le FAB
                Spacer(modifier = Modifier.height(80.dp))
            }
        }
    }
}