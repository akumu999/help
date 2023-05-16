package com.example.pethelper.Screens.Login

import android.content.Context
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.pethelper.Navigation.NavScreens
import com.example.pethelper.common.Consts
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class LoginViewModel : ViewModel() {

    val isAdmin = mutableStateOf(false)

    private val _email = MutableLiveData("")
    val email: LiveData<String> = _email

    private val _password = MutableLiveData("")
    val password: LiveData<String> = _password

    val error = mutableStateOf("")


    fun onEmailChange(email: String) {
        _email.value = email
    }

    fun onPasswordChange(password: String) {
        _password.value = password
    }

    fun login(controller: NavController, context: Context) {
        val email = email.value ?: return
        val password = password.value ?: return

        if (email.isNotEmpty() && password.isNotEmpty()) {
            FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val currentUser = FirebaseAuth.getInstance().currentUser
                        val db = Firebase.firestore
                        val userRef = db.collection("users").document(currentUser?.uid ?: "")
                        userRef.get().addOnSuccessListener { document ->
                            if (document != null) {
                                val isAdminValue = document.getBoolean("isAdmin") ?: false
                                isAdmin.value = isAdminValue
                                if (isAdminValue) {
                                    controller.navigate(NavScreens.AdminMainScreen.route)
                                } else {
                                    controller.navigate(NavScreens.DoctorScreen.route)
                                }
                            } else {
                                error.value = "User document not found"
                            }
                        }.addOnFailureListener { exception ->
                            error.value = "Failed to fetch user data"
                        }
                    } else {
                        error.value = "Incorrect email or password"
                    }
                }
        } else {
            error.value = "Please enter email and password"
        }
    }

}