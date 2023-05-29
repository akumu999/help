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
import com.example.pethelper.ui.theme.Bisque2
import com.example.pethelper.ui.theme.Bisque4

@Composable
fun AdminMainScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Bisque2),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(
            onClick = { navController.navigate(NavScreens.VeterinariansAdmin.route) },
            modifier = Modifier.fillMaxWidth().padding(start = 40.dp, end = 40.dp, top = 20.dp),
            colors = ButtonDefaults.buttonColors(backgroundColor = Bisque4), elevation = ButtonDefaults.elevation(defaultElevation = 8.dp, pressedElevation = 16.dp)
        ) {
            Text(text = "Ветеринары", color = Color.White)
        }

        Button(
            onClick = { navController.navigate(NavScreens.ProductsAdmin.route) },
            modifier = Modifier.fillMaxWidth().padding(start = 40.dp, end = 40.dp, top = 20.dp),
            colors = ButtonDefaults.buttonColors(backgroundColor = Bisque4), elevation = ButtonDefaults.elevation(defaultElevation = 8.dp, pressedElevation = 16.dp)
        ) {
            Text(text = "Услуги", color = Color.White)
        }
    }
}
