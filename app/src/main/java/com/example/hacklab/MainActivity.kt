package com.example.hacklab

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import com.example.hacklab.data.CartManager
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import com.example.hacklab.screens.MainScreen
import com.example.hacklab.ui.theme.Background
import com.example.hacklab.utils.SessionManager
import com.google.firebase.FirebaseApp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(this)
        CartManager.initialize(applicationContext)

        // Initialiser SessionManager
        SessionManager.init(this)
        SessionManager.startUserSession()

        setContent {
            val context = LocalContext.current
            val permissionLauncher = rememberLauncherForActivityResult(
                ActivityResultContracts.RequestPermission()
            ) { isGranted ->
                // Permission gérée
            }

            LaunchedEffect(Unit) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    if (ContextCompat.checkSelfPermission(
                            context,
                            Manifest.permission.POST_NOTIFICATIONS
                        ) != PackageManager.PERMISSION_GRANTED
                    ) {
                        permissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                    }
                }
            }

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