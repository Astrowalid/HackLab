package com.example.hacklab.screens

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.hacklab.R
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    onSplashFinished: () -> Unit = {}
) {
    // ===== ANIMATIONS =====

    // Animation de scale (zoom) du logo
    val infiniteTransition = rememberInfiniteTransition(label = "infinite")
    val scale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.1f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000, easing = EaseInOutCubic),
            repeatMode = RepeatMode.Reverse
        ),
        label = "scale"
    )

    // Animation de glow (lueur)
    val glow by infiniteTransition.animateFloat(
        initialValue = 0.3f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(1500, easing = EaseInOutCubic),
            repeatMode = RepeatMode.Reverse
        ),
        label = "glow"
    )

    // Démarrer le timer pour aller à LoginScreen après 3 secondes
    LaunchedEffect(Unit) {
        delay(3000) // 3 secondes
        onSplashFinished()
    }

    // ===== UI =====
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF0A0A12)),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // ===== LOGO AVEC GLOW =====
        Box(
            modifier = Modifier.size(200.dp),
            contentAlignment = Alignment.Center
        ) {
            // Glow background (cercle lumineux)
            Box(
                modifier = Modifier
                    .size(200.dp)
                    .alpha(glow * 0.3f) // Opacité qui change
                    .background(
                        color = Color(0xFFDE0000),
                        shape = androidx.compose.foundation.shape.CircleShape
                    )
            )

            // Logo avec animation scale
            androidx.compose.foundation.Image(
                painter = androidx.compose.ui.res.painterResource(id = R.drawable.hacklab_white),
                contentDescription = "HackLab Logo",
                modifier = Modifier
                    .size(150.dp)
                    .scale(scale)
            )
        }

        Spacer(modifier = Modifier.height(40.dp))

        // ===== TEXTE =====
        Text(
            text = "HackLab",
            color = Color.White,
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Security Tools Marketplace",
            color = Color(0xFF999999),
            fontSize = 14.sp,
            fontWeight = FontWeight.Normal
        )

        Spacer(modifier = Modifier.height(60.dp))

        // ===== LOADING DOTS ANIMATION =====
        LoadingDots()
    }
}

@Composable
fun LoadingDots() {
    val infiniteTransition = rememberInfiniteTransition(label = "loading")

    // Animation pour les 3 points
    val dot1Alpha by infiniteTransition.animateFloat(
        initialValue = 0.3f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(600, delayMillis = 0),
            repeatMode = RepeatMode.Reverse
        ),
        label = "dot1"
    )

    val dot2Alpha by infiniteTransition.animateFloat(
        initialValue = 0.3f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(600, delayMillis = 200),
            repeatMode = RepeatMode.Reverse
        ),
        label = "dot2"
    )

    val dot3Alpha by infiniteTransition.animateFloat(
        initialValue = 0.3f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(600, delayMillis = 400),
            repeatMode = RepeatMode.Reverse
        ),
        label = "dot3"
    )

    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Point 1
        Box(
            modifier = Modifier
                .size(8.dp)
                .background(
                    color = Color(0xFFDE0000),
                    shape = androidx.compose.foundation.shape.CircleShape
                )
                .alpha(dot1Alpha)
        )

        // Point 2
        Box(
            modifier = Modifier
                .size(8.dp)
                .background(
                    color = Color(0xFFDE0000),
                    shape = androidx.compose.foundation.shape.CircleShape
                )
                .alpha(dot2Alpha)
        )

        // Point 3
        Box(
            modifier = Modifier
                .size(8.dp)
                .background(
                    color = Color(0xFFDE0000),
                    shape = androidx.compose.foundation.shape.CircleShape
                )
                .alpha(dot3Alpha)
        )
    }
}