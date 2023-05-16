package com.example.pethelper.Screens

import Pet
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.pethelper.Navigation.NavScreens
import com.example.pethelper.R
import com.example.pethelper.Screens.Doctors.Veterinarian
import com.example.pethelper.ui.theme.Bisque1
import com.example.pethelper.ui.theme.Bisque2
import com.example.pethelper.ui.theme.Bisque4
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.toObjects
import kotlinx.coroutines.tasks.await

@Composable
fun DoctorScreen(controller: NavController) {

    val db = FirebaseFirestore.getInstance()
    val user = FirebaseAuth.getInstance().currentUser

    // Хранит список ветеринаров текущего пользователя
    var veterinarians by remember { mutableStateOf<List<Veterinarian>>(emptyList()) }

    // Получаем список ветеринаров из Firestore
    LaunchedEffect(user?.uid) {
        val querySnapshot = db.collection("veterinarians")
            .get()
            .await()
        veterinarians = querySnapshot.toObjects<Veterinarian>()
    }


    // Хранит значение для поиска
    var searchQuery by remember { mutableStateOf("") }

    // Отфильтрованный список товаров
    val filteredVeterinarian= if (searchQuery.isEmpty()) {
        veterinarians
    } else {
        veterinarians.filter { it.name.contains(searchQuery, ignoreCase = true) }
    }

    Column {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Bisque2),
            contentAlignment = Alignment.TopEnd
        ) {
            Text(
                text = "Специалисты",
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.align(Alignment.Center).padding(8.dp)
            )
        }

        TopAppBar(
            title = { Text(text = "PETHELPER", textAlign = TextAlign.Center) },
            backgroundColor = Bisque1,
            elevation = 8.dp,
            actions = {
                // TextField для поиска
                TextField(
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    modifier = Modifier
                        .padding(end = 16.dp)
                        .fillMaxWidth()
                        .size(60.dp),
                    placeholder = { Text(text = "Поиск") },
                    colors = TextFieldDefaults.textFieldColors(
                        backgroundColor = Bisque1,
                        cursorColor = Color.Black,
                        focusedIndicatorColor = Bisque1,
                        unfocusedIndicatorColor = Bisque1,
                        focusedLabelColor = Color.Black,
                        unfocusedLabelColor = Color.Black
                    ),
                    leadingIcon = {
                        Icon(
                            painter = painterResource(id = R.drawable.search),
                            contentDescription = "Search"
                        )
                    }
                )
            }
        )

        // Отображаем список Ветеринаров в LazyColumn
        LazyColumn(modifier = Modifier
            .fillMaxSize()
            .background(Bisque2)) {
            items(filteredVeterinarian) { veterinarian ->
                Card(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(vertical = 8.dp, horizontal = 16.dp)
                )
                {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 16.dp, horizontal = 16.dp)

                    ) {
                        Text(text = veterinarian.name + " " + veterinarian.midname + " " + veterinarian.surname, fontWeight = FontWeight.Bold)
                        Text(text = veterinarian.speciality)
                    }
                }
            }
        }
    }
}