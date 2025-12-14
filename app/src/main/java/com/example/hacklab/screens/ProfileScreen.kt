package com.example.hacklab.screens

import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.hacklab.R
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.hacklab.cloudinary.uploadUserProfileImage
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.userProfileChangeRequest


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Profile(navController: NavController){
    val context = LocalContext.current
   // var selectedImageUri by remember { mutableStateOf<Uri?>(null) }
    
    val currentUser = FirebaseAuth.getInstance().currentUser
    //var photoUrl by remember { mutableStateOf(currentUser?.photoUrl) }
    var displayName by remember { mutableStateOf(currentUser?.displayName ?: "User") }
    var email by remember { mutableStateOf(currentUser?.email ?: "") }

//    val imagePicker = rememberLauncherForActivityResult(
//        contract = ActivityResultContracts.GetContent()
//    ) { uri ->
//        selectedImageUri = uri
//    }

//    LaunchedEffect(selectedImageUri) {
//        selectedImageUri?.let { uri ->
//            uploadUserProfileImage(context, uri) { imageUrl ->
//                // Ici l'image est uploadée et URL mise à jour dans Firebase Auth
//                Log.d("Profile", "Image uploaded: $imageUrl")
//
//                // Ajout d'un timestamp pour forcer le rafraîchissement du cache de Coil
//                val timestamp = System.currentTimeMillis()
//                val newUrl = if (imageUrl.contains("?")) "$imageUrl&t=$timestamp" else "$imageUrl?t=$timestamp"
//
//                photoUrl = Uri.parse(newUrl)
//                selectedImageUri = null
//
//                Toast.makeText(context, "Photo de profil mise à jour !", Toast.LENGTH_SHORT).show()
//            }
//        }
//    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF0A0A12))
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = rememberAsyncImagePainter(
                    model = R.drawable.default_user,
                    contentScale = ContentScale.Crop
                ),
                contentDescription = "Profile picture",
                modifier = Modifier
                    .size(120.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )

            Text(
                text = displayName,
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                modifier = Modifier.padding(top = 16.dp)
            )

            Text(
                text = email,
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Gray,
                modifier = Modifier.padding(top = 4.dp)
            )

            Button(
                onClick = {
                    // Logique pour modifier le profil (ex: ouvrir une dialogue)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF242847),
                    contentColor = Color.White
                ),
                shape = RoundedCornerShape(10.dp),
            ) {
                Text("Edit Profile")
            }
        }

//        Button(
//            onClick = { imagePicker.launch("image/*") },
//            modifier = Modifier.padding(top = 12.dp)
//        ) {
//            Text("Change Profile Image")
//        }

        SettingsList(Modifier.padding(horizontal = 16.dp))

        Column(
            modifier = Modifier.padding(16.dp),
        ){
            Button(
                onClick = {

                    FirebaseAuth.getInstance().signOut()

                    navController.navigate(com.example.hacklab.navigation.AppNavigation.Login.route) {
                        popUpTo(0) { inclusive = true }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFDE0000),
                    contentColor = Color.White
                ),
                shape = RoundedCornerShape(10.dp),
            ) {
                Text("Log Out")
            }

        }

    }
}


@Composable
fun SettingsList(modifier: Modifier = Modifier) {
    val settingsItems = listOf(
        SettingItem("Notifications"),
        SettingItem("Confidentialité"),
        SettingItem("Sécurité"),
        SettingItem("Apparence"),
        SettingItem("Aide & Support")
    )

    Column(modifier = modifier) {
        Text(
            text = "Settings",
            fontSize = 19.sp,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        settingsItems.forEach { item ->
            SettingRow(
                item = item,
                onClick = {  }
            )
        }
    }
}

@Composable
fun SettingRow(item: SettingItem, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 7.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent),
        onClick = onClick
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(0.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                fontSize = 15.sp,
                text = item.title,
                style = MaterialTheme.typography.bodyMedium,
                color = Color.White
            )
            Icon(
                imageVector = Icons.Default.ChevronRight,
                contentDescription = "Aller vers ${item.title}",
                tint = Color.Gray
            )
        }
    }
}

data class SettingItem(val title: String)




@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun ProfileScreen(){
    Profile(navController = rememberNavController())
}
