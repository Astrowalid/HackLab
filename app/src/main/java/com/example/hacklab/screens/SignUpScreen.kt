package com.example.hacklab.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.hacklab.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.userProfileChangeRequest

@Composable
fun SignupScreen(
    onSignUpSuccess: () -> Unit,
    onLoginClick: () -> Unit
) {
    val auth = FirebaseAuth.getInstance()
    var username by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var errorMessage: String? by remember { mutableStateOf(null) }
    var showPassword by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF0A0A12))
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        // Logo
        Image(
            painter = painterResource(id = R.drawable.hacklab_white),
            contentDescription = "HackLab Logo",
            modifier = Modifier
                .width(100.dp)
                .height(100.dp)
        )

        Spacer(modifier = Modifier.height(150.dp))

        // Titre
        Text(
            text = "Create Account",
            style = MaterialTheme.typography.headlineLarge,
            color = Color.White,
            fontSize = 32.sp
        )

        Spacer(modifier = Modifier.height(35.dp))

        // Username
        OutlinedTextField(
            value = username,
            onValueChange = {
                username = it
                errorMessage = null
            },
            label = { Text("Username", color = Color(0xFF999999)) },
            leadingIcon = {
                Icon(
                    Icons.Default.Person,
                    contentDescription = "Username",
                    tint = Color(0xFF999999)
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            singleLine = true,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color(0xFFDE0000),
                unfocusedBorderColor = Color(0xFF242847),
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White,
                cursorColor = Color(0xFFDE0000)
            ),
            shape = RoundedCornerShape(8.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Email
        OutlinedTextField(
            value = email,
            onValueChange = {
                email = it
                errorMessage = null
            },
            label = { Text("Email", color = Color(0xFF999999)) },
            leadingIcon = {
                Icon(
                    Icons.Default.Email,
                    contentDescription = "Email",
                    tint = Color(0xFF999999)
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            singleLine = true,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color(0xFFDE0000),
                unfocusedBorderColor = Color(0xFF242847),
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White,
                cursorColor = Color(0xFFDE0000)
            ),
            shape = RoundedCornerShape(8.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Password
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password", color = Color(0xFF999999)) },
            leadingIcon = {
                Icon(
                    Icons.Default.Lock,
                    contentDescription = "Password",
                    tint = Color(0xFF999999)
                )
            },
            trailingIcon = {
                IconButton(onClick = { showPassword = !showPassword }) {
                    Icon(
                        imageVector = if (showPassword)
                            Icons.Default.Visibility
                        else
                            Icons.Default.VisibilityOff,
                        contentDescription = "Toggle password",
                        tint = Color(0xFF999999)
                    )
                }
            },
            visualTransformation = if (showPassword)
                VisualTransformation.None
            else
                PasswordVisualTransformation(),
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            singleLine = true,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color(0xFFDE0000),
                unfocusedBorderColor = Color(0xFF242847),
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White,
                cursorColor = Color(0xFFDE0000)
            ),
            shape = RoundedCornerShape(8.dp)
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Error message
        errorMessage?.let { error ->
            Text(
                text = error,
                color = Color(0xFFDE0000),
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(8.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
        }

        // Sign Up button
        Button(
            onClick = {
                // validation...
                auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            // Optionally set display name:
                            val user = auth.currentUser
                            val profileUpdates = userProfileChangeRequest {
                                displayName = username
                            }
                            user?.updateProfile(profileUpdates)
                            errorMessage = null
                            onSignUpSuccess()
                        } else {
                            errorMessage = task.exception?.localizedMessage ?: "Signup failed"
                        }
                    }
            },
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
                "Sign Up",
                fontSize = 18.sp,
                fontWeight = androidx.compose.ui.text.font.FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Login link
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                "Already have an account? ",
                color = Color(0xFF999999),
                fontSize = 14.sp
            )
            TextButton(
                onClick = { onLoginClick() },
                contentPadding = PaddingValues(0.dp)
            ) {
                Text(
                    "Log In",
                    color = Color(0xFFDE0000),
                    fontSize = 14.sp
                )
            }
        }
    }
}