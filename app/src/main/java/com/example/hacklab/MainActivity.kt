package com.example.hacklab

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import com.example.hacklab.screens.MainScreen
import com.example.hacklab.ui.theme.Background
import com.example.hacklab.utils.SessionManager
import com.google.firebase.FirebaseApp


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(this)
        
        // Initialiser SessionManager
        SessionManager.init(this)
        SessionManager.startUserSession()

        setContent {
            // Un conteneur global qui intercepte tous les événements tactiles
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .pointerInput(Unit) {
                        awaitPointerEventScope {
                            while (true) {
                                awaitPointerEvent()
                                // À chaque interaction, on redémarre le timer
                                SessionManager.startUserSession()
                            }
                        }
                    }
            ) {
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
}