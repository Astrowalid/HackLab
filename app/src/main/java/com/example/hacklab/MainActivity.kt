package com.example.hacklab

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.hacklab.screens.ProductListScreen
import com.example.hacklab.ui.theme.Background
import com.example.hacklab.ui.theme.HackLabTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HackLabTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Background
                ) {
                    ProductListScreen(
                        onProductClick = { productId ->
                            println("Produit cliqué: $productId")
                        }
                    )
                }
            }
        }
    }
}
