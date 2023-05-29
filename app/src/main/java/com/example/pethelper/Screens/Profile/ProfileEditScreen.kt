package com.example.pethelper.Screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.pethelper.Navigation.NavScreens
import com.example.pethelper.ui.theme.Bisque1
import com.example.pethelper.ui.theme.Bisque2
import com.example.pethelper.ui.theme.Bisque4
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

@Composable
fun ProfileEditScreen(controller: NavController) {
    val user = remember { FirebaseAuth.getInstance().currentUser }
    val db = Firebase.firestore
    val userRef = db.collection("users").document(user!!.uid)

    var name by remember { mutableStateOf("") }
    var surname by remember { mutableStateOf("")}
    var age by remember { mutableStateOf("") }
    var bio by remember { mutableStateOf("") }

    LaunchedEffect(user) {
        userRef.get().addOnSuccessListener { document ->
            if (document != null) {
                name = document.getString("name") ?: ""
                surname = document.getString("surname") ?: ""
                age = document.getString("age") ?: ""
                bio = document.getString("bio") ?: ""
            }
        }
    }

    Column(modifier = Modifier.fillMaxSize().background(Bisque2)) {

        IconButton(onClick = { controller.navigate(NavScreens.ProfileScreen.route) }) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Back button"
            )
        }
        OutlinedTextField(
            value = name,
            onValueChange = { newValue ->
                if (newValue.all { it.isLetter() }) {
                    name = newValue
                }
            },
            label = { Text(text = "Имя") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )

        OutlinedTextField(
            value = surname,
            onValueChange = { newValue ->
                if (newValue.all { it.isLetter() }) {
                    surname = newValue
                }
            },
            label = { Text(text = "Фамилия") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )

        OutlinedTextField(
            value = age,
            onValueChange = { newValue ->
                if (newValue.all { it.isDigit() }) {
                    age = newValue
                }
            },
            label = { Text(text = "Возраст") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )

        OutlinedTextField(
            value = bio,
            onValueChange = { bio = it },
            label = { Text(text = "Информация о себе") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )


        val data = mapOf(
            "name" to name,
            "surname" to surname,
            "age" to age,
            "bio" to bio,
            "updatedAt" to FieldValue.serverTimestamp()
        )


        Button(
            onClick = {
                controller.navigate(NavScreens.ProfileScreen.route)
                userRef.set(data)
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp), colors = ButtonDefaults.buttonColors(backgroundColor = Bisque4)
        ) {
            Text(text = "Сохранить", color = Color.White)
        }
    }
}
