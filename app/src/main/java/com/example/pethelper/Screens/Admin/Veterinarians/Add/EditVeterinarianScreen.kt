package com.example.pethelper.Screens.Admin.Veterinarians.Add

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.pethelper.Navigation.NavScreens
import com.example.pethelper.ui.theme.Bisque2
import com.example.pethelper.ui.theme.Bisque4
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

@Composable
fun EditVeterinarianScreen(veterinarianID: String, controller: NavController) {
    val db = Firebase.firestore
    val veterinarianRef = db.collection("veterinarians").document(veterinarianID)

    val name = remember { mutableStateOf("") }
    val surname = remember { mutableStateOf("") }
    val midname = remember { mutableStateOf("") }
    val education = remember { mutableStateOf("") }
    val speciality = remember { mutableStateOf("") }
    val workExperience = remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        // Получите данные о ветеринаре из Firebase Firestore
        val veterinarianSnapshot = veterinarianRef.get().await()
        if (veterinarianSnapshot.exists()) {
            // Если документ существует, получите данные
            val veterinarianData = veterinarianSnapshot.data
            name.value = veterinarianData?.get("name") as? String ?: ""
            surname.value = veterinarianData?.get("surname") as? String ?: ""
            midname.value = veterinarianData?.get("midname") as? String ?: ""
            education.value = veterinarianData?.get("education") as? String ?: ""
            speciality.value = veterinarianData?.get("speciality") as? String ?: ""
            workExperience.value = veterinarianData?.get("work_experience") as? String ?: ""
        }
    }

    Column(
        modifier = Modifier.background(Bisque2)
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Редактирование данных ветеринара",
            style = MaterialTheme.typography.h6,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        OutlinedTextField(
            value = name.value,
            onValueChange = { newValue ->
                name.value = newValue
            },
            label = { Text(text = "Имя") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        )
        OutlinedTextField(
            value = surname.value,
            onValueChange = { newValue ->
                surname.value = newValue
            },
            label = { Text(text = "Фамилия") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        )
        OutlinedTextField(
            value = midname.value,
            onValueChange = { newValue ->
                midname.value = newValue
            },
            label = { Text(text = "Отчество") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        )
        OutlinedTextField(
            value = education.value,
            onValueChange = { newValue ->
                education.value = newValue
            },
            label = { Text(text = "Образование") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        )
        OutlinedTextField(
            value = speciality.value,
            onValueChange = { newValue ->
                speciality.value = newValue
            },
            label = { Text(text = "Специализация") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        )
        OutlinedTextField(
            value = workExperience.value,
            onValueChange = { newValue ->
                workExperience.value = newValue
            },
            label = { Text(text = "Опыт работы") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        )
        Button(
            colors = ButtonDefaults.buttonColors(backgroundColor = Bisque4),
            onClick = {
                val veterinarianData = hashMapOf(
                    "name" to name.value,
                    "surname" to surname.value,
                    "midname" to midname.value,
                    "education" to education.value,
                    "speciality" to speciality.value,
                    "work_experience" to workExperience.value
                )
                veterinarianRef.set(veterinarianData)
                controller.navigate(NavScreens.VeterinariansAdmin.route)
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Сохранить изменения", color = Color.White)
        }
    }
}
