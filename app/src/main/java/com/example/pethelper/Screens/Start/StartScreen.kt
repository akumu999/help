package com.example.pethelper.Screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.pethelper.Navigation.NavScreens
import com.example.pethelper.auth
import com.example.pethelper.ui.theme.Bisque2
import com.example.pethelper.ui.theme.Bisque4
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

@Composable
fun StartScreen(controller: NavController){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Bisque2),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        auth = Firebase.auth
        Text(
            text = "PETHELPER",
            style = MaterialTheme.typography.h4,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        Button(colors = ButtonDefaults.buttonColors(backgroundColor = Bisque4), onClick = {
            controller.navigate(NavScreens.LoginScreen.route)
        },  modifier = Modifier
            .padding(top = 400.dp)
            .width(250.dp)
            .height(50.dp)
            .clip(
                RoundedCornerShape(25.dp)
            ), elevation = ButtonDefaults.elevation(defaultElevation = 8.dp, pressedElevation = 16.dp)){
            Text(text = "Войти", color = Color.White, fontSize = 20.sp)
        }
        Button(modifier = Modifier
            .padding(top = 10.dp)
            .width(250.dp)
            .height(50.dp)
            .clip(
                RoundedCornerShape(25.dp)
            ),colors = ButtonDefaults.buttonColors(backgroundColor = Bisque4), elevation = ButtonDefaults.elevation(defaultElevation = 8.dp, pressedElevation = 16.dp),
            onClick = {
            controller.navigate(NavScreens.RegisterScreen.route)
        }){
            Text(text = "Регистрация", color = Color.White, fontSize = 20.sp)
        }
    }
}