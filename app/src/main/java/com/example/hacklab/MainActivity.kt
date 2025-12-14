package com.example.hacklab

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.hacklab.data.CartManager
import com.example.hacklab.screens.MainScreen
import com.example.hacklab.ui.theme.Background
import com.google.firebase.FirebaseApp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(this)
        CartManager.initialize(applicationContext)
        setContent {
            Surface(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Background),
                color = Background
            ) {
                MainScreen()
            }
        }
    }
}