package com.example.pethelper.Screens.Admin

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase



import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.Text
import com.example.pethelper.Navigation.NavScreens

@Composable
fun AdminMainScreen(navController: NavController) {
    Column {
        Button(
            onClick = { navController.navigate(NavScreens.VeterinariansAdmin.route) },
            modifier = Modifier.padding(16.dp)
        ) {
            Text(text = "Ветеринары")
        }

        Button(
            onClick = { navController.navigate(NavScreens.ProductsAdmin.route) },
            modifier = Modifier.padding(16.dp)
        ) {
            Text(text = "Услуги")
        }

        Button(
            onClick = { navController.navigate(NavScreens.UsersAdmin.route) },
            modifier = Modifier.padding(16.dp)
        ) {
            Text(text = "Пользователи")
        }
    }
}


/*
@Composable
fun AdminMainScreen() {
    val veterinarianName = remember { mutableStateOf("") }
    val productName = remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        TextField(
            value = veterinarianName.value,
            onValueChange = { veterinarianName.value = it },
            label = { Text("Имя ветеринара") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = { addVeterinarian(veterinarianName.value) },
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text(text = "Добавить ветеринара")
        }
        Spacer(modifier = Modifier.height(32.dp))
        TextField(
            value = productName.value,
            onValueChange = { productName.value = it },
            label = { Text("Название продукта") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = { addProduct(productName.value) },
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text(text = "Добавить продукт")
        }
    }
}

private fun addVeterinarian(name: String) {
    val db = Firebase.firestore
    val veterinariansRef = db.collection("veterinarians")

    val data = hashMapOf(
        "name" to name
    )

    veterinariansRef.add(data)
        .addOnSuccessListener {
            // Veterinarian added successfully
        }
        .addOnFailureListener { exception ->
            // Handle error while adding veterinarian
        }
}

private fun addProduct(name: String) {
    val db = Firebase.firestore
    val productsRef = db.collection("products")

    val data = hashMapOf(
        "name" to name
    )

    productsRef.add(data)
        .addOnSuccessListener {
            // Product added successfully
        }
        .addOnFailureListener { exception ->
            // Handle error while adding product
        }
}
*/
