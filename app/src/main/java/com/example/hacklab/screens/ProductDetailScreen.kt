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
import coil.compose.AsyncImage
import com.example.hacklab.data.CartManager
import com.example.hacklab.R
import com.example.hacklab.viewmodel.ProductViewModel
import com.example.hacklab.data.ProductRepository
import com.example.hacklab.utils.NotificationHelper

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductDetailScreen(
    productId: Int? = 1,
    onBackClick: () -> Unit = {},
    onCartClick: () -> Unit = {},
    navController: NavController? = null,
    viewModel: ProductViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
    val viewModel: ProductViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
    val product = viewModel.getProductById(productId ?: 1)
    println("ProductDetailScreen - Product ID: $productId, Product: ${product?.name}")
    val cartItemsCount by remember { derivedStateOf { CartManager.getCartItemsCount() } }
    val context = LocalContext.current

    // Retrieve product details using the ID
    val product = productId?.let { ProductRepository.getProductById(it) }

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
            },
            actions = {
                BadgedBox(
                    badge = {
                        if (cartItemsCount > 0) {
                            Badge {
                                Text(
                                    text = cartItemsCount.toString(),
                                    color = Color.White,
                                    fontSize = 12.sp
                                )
                            }
                        }
                    }
                ) {
                    IconButton(onClick = {
                       onCartClick()
                    }) {
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

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .weight(1f)
        ) {
            item {
                ProductDetailImage(
                    imageResId = product.imageResId,
                    imageUrl = product.imageUrl,
                    productName = product.name
                )
            }

            item {
                ProductDetailInfo(
                    name = product.name,
                    description = product.description,
                    price = product.price
                )
            }

            item {
                if (product.specifications.isNotEmpty()) {
                    SpecificationsSection(
                        specifications = product.specifications
                    )
                }
            }

            item {
                if (product.safetyDisclaimer.isNotEmpty()) {
                    SafetyDisclaimerSection(
                        disclaimer = product.safetyDisclaimer
                    )
                }
            }

            item {
                Spacer(modifier = Modifier.height(100.dp))
            }
        }

        AddToCartButton(
            price = product.price,
            onAddToCart = {
                println("🛒 Add to Cart button clicked for: ${product.name}")
                CartManager.addToCart(product)
            }
        )
    }
}

@Composable
fun ProductDetailImage(
    imageResId: Int,
    imageUrl: String = "",
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
        if (imageUrl.isNotEmpty()) {
            AsyncImage(
                model = imageUrl,
                contentDescription = productName,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Fit,
                placeholder = painterResource(id = R.drawable.product_1),
                error = painterResource(id = R.drawable.product_1)
            )
        } else if (imageResId != 0) {
            Image(
                painter = painterResource(id = imageResId),
                contentDescription = productName,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Fit
            )
        } else {
            Image(
                painter = painterResource(id = R.drawable.product_1),
                contentDescription = productName,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Fit
            )
        }
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
            text = "$${String.format("%.2f", price)}",
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