package com.example.hacklab.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.hacklab.R
import com.example.hacklab.navigation.AppNavigation

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopNavigationBar(navController: NavController) {
    TopAppBar(
        title = {},
        navigationIcon = {
            IconButton(
                onClick = {
                    navController.navigate(AppNavigation.ProductList.route) {
                        popUpTo(AppNavigation.ProductList.route) { inclusive = true }
                    }
                }
            ) {
                Image(
                    painter = painterResource(R.drawable.hacklab_white),
                    contentDescription = "Logo",
                    modifier = Modifier.size(40.dp)
                )
            }
        },
        actions = {
            IconButton(
                onClick = {
                    navController.navigate(AppNavigation.Card.route)
                }
            ) {
                Icon(
                    imageVector = Icons.Default.ShoppingCart,
                    contentDescription = "Cart",
                    tint = Color.White
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color(0xFF0A0A12)
        )
    )
}

@Preview(showBackground = true)
@Composable
fun TopNavigationBarPreview() {
    TopNavigationBar(navController = rememberNavController())
}
