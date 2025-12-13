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
    
    // Exemple de données produit (normalement récupérées via un ViewModel)
    val productTitle = "Product $productId"
    val productPrice = "$${100 + (productId ?: 0)}"
    val productDescription = "Ceci est une description détaillée du produit. Elle explique les caractéristiques et avantages du produit."

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = { 
                    // Action ajout au panier
                    Toast.makeText(context, "Added to cart", Toast.LENGTH_SHORT).show()
                    
                    // Envoyer une notification locale
                    NotificationHelper.showNotification(
                        context,
                        "Produit ajouté !",
                        "Le produit $productTitle a été ajouté à votre panier.",
                        productId ?: 2000 // ID unique basé sur le produit
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
                    painter = painterResource(id = R.drawable.design), // Image placeholder
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            }

            Column(modifier = Modifier.padding(16.dp)) {
                // Titre et Prix
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = productTitle,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    Text(
                        text = productPrice,
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
                    text = productDescription,
                    fontSize = 14.sp,
                    color = Color.Gray,
                    lineHeight = 20.sp
                )
            }
        }
    }
}