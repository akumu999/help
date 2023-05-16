package com.example.pethelper.Screens.Admin.Products

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.pethelper.Screens.Admin.Products.Add.Product
import com.google.firebase.firestore.FirebaseFirestore

@Composable
fun ProductsAdd(navController: NavController, viewModel: ProductsViewModel = viewModel()) {
    val firestore = FirebaseFirestore.getInstance()
    val productsCollection = firestore.collection("products")

    val productNameState = remember { mutableStateOf("") }
    val productDescriptionState = remember { mutableStateOf("") }
    val productPriceState = remember { mutableStateOf("") }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text(
            text = "Добавить услугу",
            style = MaterialTheme.typography.h5,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        TextField(
            value = productNameState.value,
            onValueChange = { productNameState.value = it },
            label = { Text("Название услуги") },
            modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)
        )


        TextField(
            value = productPriceState.value,
            onValueChange = { productPriceState.value = it },
            label = { Text("Цена услуги") },
            modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)
        )

        Button(
            onClick = {
                val name = productNameState.value
                val cost = productPriceState.value

                val product = Product(name, cost)

                // Add the product to Firestore
                productsCollection.add(product)
                    .addOnSuccessListener {
                        // Successfully added
                        navController.popBackStack()
                    }
                    .addOnFailureListener { exception ->
                        // Handle failure
                    }
            },
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text(text = "Добавить")
        }
    }
}
