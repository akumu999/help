package com.example.pethelper.Screens.Profile


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.pethelper.Navigation.NavScreens
import com.example.pethelper.R
import com.example.pethelper.ui.theme.Bisque1
import com.example.pethelper.ui.theme.Bisque2
import com.example.pethelper.ui.theme.Bisque4
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

@Composable
fun ProfileScreen(controller: NavController) {
    val user = remember { FirebaseAuth.getInstance().currentUser }
    val db = Firebase.firestore
    val userRef = db.collection("users").document(user!!.uid)

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("")}
    var name by remember { mutableStateOf("") }
    var surname by remember { mutableStateOf("") }
    var age by remember { mutableStateOf("") }
    var bio by remember { mutableStateOf("") }

    LaunchedEffect(user) {
        userRef.get().addOnSuccessListener { document ->
            if (document != null) {
                email = document.getString("email") ?: ""
                password = document.getString("password") ?: ""
                name = document.getString("name") ?: ""
                surname = document.getString("surname") ?: ""
                age = document.getString("age") ?: ""
                bio = document.getString("bio") ?: ""
            }
        }
    }
    Column(modifier = Modifier
        .fillMaxSize()
        .background(Bisque2)) {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Bisque2),
            contentAlignment = Alignment.TopEnd
        ) {
            Text(
                text = "Мой профиль",
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(8.dp)
            )
            FloatingActionButton(
                onClick = { controller.navigate(NavScreens.ProfileEditScreen.route) },
                backgroundColor = Color.Blue,
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .size(40.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "Add",
                    tint = Color.White,
                    modifier = Modifier
                        .size(400.dp)
                        .background(Color.Green)
                )
            }
        }
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
                .clickable {
                    controller.navigate(NavScreens.ProfileEditScreen.route)
                    userRef.update(
                        mapOf(
                            "email" to email,
                            "password" to password,
                            "name" to name,
                            "age" to age,
                            "bio" to bio,
                            "updatedAt" to FieldValue.serverTimestamp()
                        )
                    )
                },
            elevation = 4.dp,
        ) {
            Column() {

                Text(
                    text = "Имя: " + name,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                )

                Text(
                    text = "Фамилия: " + surname,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                )

                Text(
                    text = "Возраст: " + age,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                )

                Text(
                    text = "О себе: " + bio,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                )
            }
        }
    }
}
