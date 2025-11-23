package com.example.hacklab.screens


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.hacklab.R

// Modèle détaillé du produit
data class ProductDetail(
    val id: Int,
    val name: String,
    val description: String,
    val price: Double,
    val imageResId: Int,
    val specifications: Map<String, String>,
    val safetyDisclaimer: String
)

// Données de test
val sampleProductDetail = ProductDetail(
    id = 1,
    name = "Stealth Recon Kit",
    description = "A compact, Raspberry Pi-based tool for covert network reconnaissance and penetration testing. Includes pre-configured scripts for passive and active scanning, credential harvesting, and exploit execution.",
    price = 149.99,
    imageResId = R.drawable.hacklab,
    specifications = mapOf(
        "Platform" to "Raspberry Pi 4",
        "Storage" to "64GB MicroSD",
        "Connectivity" to "WiFi 802.11ac",
        "Power" to "USB-C",
        "Bluetooth" to "5.0"
    ),
    safetyDisclaimer = "This tool is intended for ethical hacking, security testing and educational purposes only. Use responsibly and within legal boundaries. We are not responsible for misuse or damage caused by this product."
)

@Composable
fun ProductDetailScreen(
    productId: Int? = 1,
    onBackClick: () -> Unit = {},
    onAddToCart: () -> Unit = {}
) {
    // Utiliser les données de test pour l'instant
    val product = sampleProductDetail

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF0A0A12))
    ) {
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
                SpecificationsSection(
                    specifications = product.specifications
                )
            }

            // Safety Disclaimer
            item {
                SafetyDisclaimerSection(
                    disclaimer = product.safetyDisclaimer
                )
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
            containerColor = Color(0xFFF5E6D3) // Fond beige clair comme dans l'image
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
        // Nom du produit
        Text(
            text = name,
            color = Color.White,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )

        // Description
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
        // Titre "Specifications"
        Text(
            text = "Specifications",
            color = Color(0xFFDE0000), // Rouge
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold
        )

        // Grille des spécifications
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
        // Titre "Safety Disclaimer"
        Text(
            text = "Safety Disclaimer",
            color = Color(0xFFFFC107), // Rouge
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold
        )

        // Texte du disclaimer
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
        // Affichage du prix
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

        // Bouton "Add to Cart"
        Button(
            onClick = onAddToCart,
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFDE0000), // Rouge
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
