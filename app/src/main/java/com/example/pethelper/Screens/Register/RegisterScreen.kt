package com.example.pethelper.Screens.Register

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.pethelper.Navigation.NavScreens
import com.example.pethelper.ui.theme.Bisque2
import com.example.pethelper.ui.theme.Bisque4
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

private fun isFormFilled(
    email: String,
    password: String,
    name: String,
    surname: String
): Boolean {
    return email.isNotEmpty() && password.isNotEmpty() && name.isNotEmpty()
}

@Composable
fun RegisterScreen(
    viewModel: RegisterViewModel,
    controller: NavController
) {
    val email by viewModel.email.observeAsState("")
    val password by viewModel.password.observeAsState("")

    val user = remember { FirebaseAuth.getInstance().currentUser }
    val db = Firebase.firestore
    val userRef = db.collection("users").document(user!!.uid)

    var name by remember { mutableStateOf("") }
    var surname by remember { mutableStateOf("")}
    var age by remember { mutableStateOf("") }
    var bio by remember { mutableStateOf("") }
    var isAdmin by remember { mutableStateOf(false) }
    var isVet by remember { mutableStateOf(false) }

    LaunchedEffect(user) {
        userRef.get().addOnSuccessListener { document ->
            if (document != null) {
                name = document.getString("name") ?: ""
                surname = document.getString("surname") ?: ""
                age = document.getString("age") ?: ""
                bio = document.getString("bio") ?: ""
                isAdmin = document.getBoolean("isAdmin") ?: false
                isVet = document.getBoolean("isVet") ?: false
            }
        }
    }


    val isFieldsFilled = remember {
        mutableStateOf(false)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Bisque2)
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Регистрация")
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = email,
            onValueChange = { viewModel.onEmailChange(it) },
            label = { Text("E-mail") },
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color.Black,
                unfocusedBorderColor = Color.Black,
                focusedLabelColor = Color.Black,
                unfocusedLabelColor = Color.Black,
                cursorColor = Color.Black
            ),
            singleLine = true,
            isError = email.isEmpty()
        )
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = password,
            onValueChange = { viewModel.onPasswordChange(it) },
            label = { Text("Пароль") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color.Black,
                unfocusedBorderColor = Color.Black,
                focusedLabelColor = Color.Black,
                unfocusedLabelColor = Color.Black,
                cursorColor = Color.Black
            ),
            singleLine = true,
            isError = password.isEmpty()
        )
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = name,
            onValueChange = {
                name = it
                isFieldsFilled.value = isFormFilled(email, password, name, surname)
            },
            label = { Text("Имя") },
            modifier = Modifier.fillMaxWidth(),
            colors =            TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color.Black,
                unfocusedBorderColor = Color.Black,
                focusedLabelColor = Color.Black,
                unfocusedLabelColor = Color.Black,
                cursorColor = Color.Black
            ),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            singleLine = true,
            isError = name.isEmpty()
        )
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = surname,
            onValueChange = {
                surname = it
                isFieldsFilled.value = isFormFilled(email, password, name, surname)
            },
            label = { Text("Фамилия") },
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color.Black,
                unfocusedBorderColor = Color.Black,
                focusedLabelColor = Color.Black,
                unfocusedLabelColor = Color.Black,
                cursorColor = Color.Black
            ),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            singleLine = true,
            isError = surname.isEmpty()
        )
        Spacer(modifier = Modifier.height(32.dp))

        val registrationError by viewModel.registrationError.observeAsState(null)

        if (registrationError != null) {
            Text(
                text = registrationError.toString(),
                color = Color.Red,
                modifier = Modifier.padding(bottom = 8.dp)
            )
        }

        Button(
            onClick = {
                viewModel.register(name, surname, age, bio, isAdmin, isVet) {
                    controller.navigate(NavScreens.DoctorScreen.route)
                }
            },
            colors = ButtonDefaults.buttonColors(backgroundColor = Bisque4), elevation = ButtonDefaults.elevation(defaultElevation = 8.dp, pressedElevation = 16.dp),
            modifier = Modifier.fillMaxWidth(),
            enabled = isFieldsFilled.value // Enable button only when all fields are filled
        ) {
            Text(text = "Зарегистрироваться")
        }
    }
}

