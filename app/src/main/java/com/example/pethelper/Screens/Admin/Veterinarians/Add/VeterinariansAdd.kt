package com.example.pethelper.Screens.Admin.Veterinarians.Add

import androidx.compose.foundation.background
import com.example.pethelper.Screens.Admin.Products.ProductsViewModel
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.pethelper.Screens.Admin.Products.Add.Product
import com.example.pethelper.Screens.Admin.Products.VeterinariansViewModel
import com.example.pethelper.ui.theme.Bisque2
import com.example.pethelper.ui.theme.Bisque4
import com.google.firebase.firestore.FirebaseFirestore
import java.util.*

@Composable
fun VeterinariansAdd(navController: NavController, viewModel: VeterinariansViewModel = viewModel()) {
    val firestore = FirebaseFirestore.getInstance()
    val veterinarianCollection = firestore.collection("veterinarians")

    val id = remember { mutableStateOf(UUID.randomUUID().toString()) }
    val name = remember { mutableStateOf("") }
    val surname = remember { mutableStateOf("") }
    val midname = remember { mutableStateOf("") }
    val education = remember { mutableStateOf("") }
    val speciality = remember { mutableStateOf("") }
    val work_experience = remember { mutableStateOf("") }

    Column(modifier = Modifier.background(Bisque2)
        .fillMaxSize()
        .padding(16.dp)) {
        Text(
            text = "Добавить Ветеринара",
            style = MaterialTheme.typography.h5,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        TextField(
            value = surname.value,
            onValueChange = { surname.value = it },
            label = { Text("Фамилия") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        )


        TextField(
            value = name.value,
            onValueChange = { name.value = it },
            label = { Text("Имя") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        )

        TextField(
            value = midname.value,
            onValueChange = { midname.value = it },
            label = { Text("Отчество") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        )


        TextField(
            value = education.value,
            onValueChange = { education.value = it },
            label = { Text("Образование") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        )

        TextField(
            value = speciality.value,
            onValueChange = { speciality.value = it },
            label = { Text("Специальность") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        )

        TextField(
            value = work_experience.value,
            onValueChange = { work_experience.value = it },
            label = { Text("Опыт работы") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        )

        Button(colors = ButtonDefaults.buttonColors(backgroundColor = Bisque4),
            elevation = ButtonDefaults.elevation(defaultElevation = 8.dp, pressedElevation = 16.dp),
            onClick = {
                val id = id.value
                val name = name.value
                val surname = surname.value
                val midname = midname.value
                val education = education.value
                val speciality = speciality.value
                val work_experience = work_experience.value
                val veterinarian = Veterinarian(id, name, surname, midname, education, speciality, work_experience)

                // Add the product to Firestore
                veterinarianCollection.add(veterinarian)
                    .addOnSuccessListener {
                        viewModel.fetchVeterinarians()
                        navController.popBackStack()

                    }
                    .addOnFailureListener { exception ->
                        viewModel.fetchVeterinarians()
                    }
            },
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text(text = "Добавить")
        }
    }
}
