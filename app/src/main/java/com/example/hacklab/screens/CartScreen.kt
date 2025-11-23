package com.example.hacklab.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.hacklab.R

data class CartItem(
    val id: Int,
    val name: String,
    val description: String,
    val price: Double,
    val quantity: Int = 1
)

@Composable
fun CardScreen(navController: NavController) {
    val cartItems = remember {
        mutableStateOf(
            listOf(
                CartItem(
                    id = 1,
                    name = "Cyber Breach Kit",
                    description = "Advanced Pentesting Tool",
                    price = 149.99
                ),
                CartItem(
                    id = 2,
                    name = "Network Sniffer Pro",
                    description = "Network Analysis Suite",
                    price = 99.99
                ),
                CartItem(
                    id = 3,
                    name = "WebScan Elite",
                    description = "Web Application Scanner",
                    price = 79.99
                )
            )
        )
    }

    val subtotal = cartItems.value.sumOf { it.price * it.quantity }
    val shipping = 0.00
    val taxes = subtotal * 0.08 // 8% tax
    val total = subtotal + shipping + taxes

    val agreedToTerms = remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF0A0A12))
            .verticalScroll(rememberScrollState())
    ) {

        Column(
            modifier = Modifier.padding(horizontal = 6.dp)
        ) {
            cartItems.value.forEach { item ->
                CartItemCard(
                    item = item,
                    onRemove = {
                        cartItems.value = cartItems.value.filter { it.id != item.id }
                    }
                )
                Spacer(modifier = Modifier.height(12.dp))
            }
        }


        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(6.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color.Transparent
            ),
            shape = RoundedCornerShape(12.dp)
        ) {
            Column(
                modifier = Modifier.padding(6.dp)
            ) {

                OrderSummaryRow("Subtotal", "$${String.format("%.2f", subtotal)}")
                Spacer(modifier = Modifier.height(8.dp))

                OrderSummaryRow("Shipping", "$${String.format("%.2f", shipping)}")
                Spacer(modifier = Modifier.height(8.dp))

                OrderSummaryRow("Taxes", "$${String.format("%.2f", taxes)}")

                Spacer(modifier = Modifier.height(12.dp))
                Spacer(modifier = Modifier.height(12.dp))

                OrderSummaryRow(
                    "Total",
                    "$${String.format("%.2f", total)}",
                    isTotal = true
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 6.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = agreedToTerms.value,
                onCheckedChange = { agreedToTerms.value = it },
                colors = androidx.compose.material3.CheckboxDefaults.colors(
                    checkedColor = Color(0xFFDE0000),
                    uncheckedColor = Color.Gray
                )
            )

            Text(
                text = "For educational use only",
                color = Color.White,
                fontSize = 14.sp
            )
        }

        Spacer(modifier = Modifier.height(20.dp))


        Button(
            onClick = {
                if (agreedToTerms.value) {

                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .height(56.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFDE0000),
                contentColor = Color.White
            ),
            shape = RoundedCornerShape(12.dp),
            enabled = agreedToTerms.value && cartItems.value.isNotEmpty()
        ) {
            Text(
                text = "Checkout",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(32.dp))
    }
}

@Composable
fun CartItemCard(item: CartItem, onRemove: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = Color.Transparent
        ),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(6.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Box(
                modifier = Modifier
                    .size(60.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color(0xFF242847)),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.souris_optique_usb),
                    contentDescription = "${item.name} image",
                    modifier = Modifier
                        .size(60.dp)
                        .clip(RoundedCornerShape(8.dp)),
                    contentScale = ContentScale.Crop
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = item.name,
                    color = Color.White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = item.description,
                    color = Color.Gray,
                    fontSize = 14.sp
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "$${String.format("%.2f", item.price)}",
                    color = Color(0xFFFFFFFF),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            IconButton(
                onClick = onRemove,
                modifier = Modifier.size(24.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Remove item",
                    tint = Color.Gray
                )
            }
        }
    }
}

@Composable
fun OrderSummaryRow(label: String, value: String, isTotal: Boolean = false) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 15.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            color = Color.White,
            fontSize = 16.sp,
            fontWeight = if (isTotal) FontWeight.Bold else FontWeight.Normal
        )
        Text(
            text = value,
            color = Color.White,
            fontSize = if (isTotal) 16.sp else 14.sp,
            fontWeight = if (isTotal) FontWeight.Bold else FontWeight.Normal
        )
    }
}

@Preview(showBackground = true)
@Composable
fun CardScreenPreview() {
    CardScreen(navController = rememberNavController())
}

