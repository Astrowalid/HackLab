package com.example.hacklab.screens
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.hacklab.R
import com.example.hacklab.navigation.AppNavigation

@Composable
fun SignupScreen(
    onSignUpSuccess: () -> Unit,
    onLoginClick: () -> Unit
) {

    var username by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF0A0A12)),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.hacklab_white),
            contentDescription = "Application logo",
            modifier = Modifier
                .fillMaxWidth()
                .size(65.dp)
                .padding(top = 25.dp)
        )
        Spacer(modifier = Modifier.height(180.dp))

        Card(
            modifier = Modifier
                .fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = Color(0xFF0A0A12)
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxSize()
                    .padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(23.dp)
            ) {

                Text(
                    text = "Sign Up",
                    color = Color.White,
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )

                TextField(
                    value = username,
                    onValueChange = { username = it },
                    label = { Text("Username", color = Color(0xFF9194C7)) },
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth(),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color(0xE8434A70),
                        unfocusedContainerColor = Color(0xFF242847),
                        focusedTextColor = Color.Black,
                        unfocusedTextColor = Color.White,
                        cursorColor = Color.Blue,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        focusedLabelColor = Color.Blue,
                        unfocusedLabelColor = Color.Gray,
                    )
                )

                TextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("Email", color = Color(0xFF9194C7)) },
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth(),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color(0xE8434A70),
                        unfocusedContainerColor = Color(0xFF242847),
                        focusedTextColor = Color.Black,
                        unfocusedTextColor = Color.White,
                        cursorColor = Color.Blue,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        focusedLabelColor = Color.Blue,
                        unfocusedLabelColor = Color.Gray,
                    )
                )

                TextField(
                    value = password,
                    onValueChange = { password = it },
                    label = { Text("Password", color = Color(0xFF9194C7)) },
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth(),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color(0xE8434A70),
                        unfocusedContainerColor = Color(0xFF242847),
                        focusedTextColor = Color.Black,
                        unfocusedTextColor = Color.White,
                        cursorColor = Color.Blue,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        focusedLabelColor = Color.Blue,
                        unfocusedLabelColor = Color.Gray,
                    )
                )

                Button(
                    onClick = {
                        onSignUpSuccess()
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFDE0000),
                        contentColor = Color.White
                    ),
                    shape = RoundedCornerShape(10.dp),
                ) {
                    Text("Sign Up")
                }


                Button(
                    onClick = {
                        onLoginClick()
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF131326),
                        contentColor = Color.White
                    ),
                    shape = RoundedCornerShape(10.dp),
                ) {
                    Text("Login")
                }
            }
        }
    }
}
