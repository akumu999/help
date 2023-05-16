package com.example.pethelper.Screens.Admin.Veterinarians.Add

import com.example.pethelper.Screens.Admin.Products.ProductsViewModel
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
import com.example.pethelper.Screens.Admin.VeterinariansViewModel
import com.google.firebase.firestore.FirebaseFirestore

@Composable
fun VeterinariansAdd(navController: NavController, viewModel: VeterinariansViewModel = viewModel()) {
    val firestore = FirebaseFirestore.getInstance()
    val veterinarianCollection = firestore.collection("veterinarians")

    val name = remember { mutableStateOf("") }
    val surname = remember { mutableStateOf("") }
    val midname = remember { mutableStateOf("") }
    val education = remember { mutableStateOf("") }
    val speciality = remember { mutableStateOf("") }
    val work_experience = remember { mutableStateOf("") }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text(
            text = "Добавить услугу",
            style = MaterialTheme.typography.h5,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        TextField(
            value = surname.value,
            onValueChange = { surname.value = it },
            label = { Text("Название услуги") },
            modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)
        )


        TextField(
            value = name.value,
            onValueChange = { name.value = it },
            label = { Text("Цена услуги") },
            modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)
        )

        TextField(
            value = midname.value,
            onValueChange = { midname.value = it },
            label = { Text("Цена услуги") },
            modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)
        )

        TextField(
            value = midname.value,
            onValueChange = { midname.value = it },
            label = { Text("Цена услуги") },
            modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)
        )

        TextField(
            value = education.value,
            onValueChange = { education.value = it },
            label = { Text("Цена услуги") },
            modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)
        )

        TextField(
            value = speciality.value,
            onValueChange = { speciality.value = it },
            label = { Text("Цена услуги") },
            modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)
        )

        TextField(
            value = work_experience.value,
            onValueChange = { work_experience.value = it },
            label = { Text("Цена услуги") },
            modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)
        )

        Button(
            onClick = {
                val name = name.value
                val surname = surname.value
                val midname = midname.value
                val education = education.value
                val speciality = speciality.value
                val work_experience = work_experience.value
                val veterinarian = Veterinarian(name, surname, midname, education, speciality, work_experience)

                // Add the product to Firestore
                veterinarianCollection.add(veterinarian)
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
