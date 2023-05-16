package com.example.pethelper.Screens.Register

import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.pethelper.Navigation.NavScreens
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import org.checkerframework.checker.units.qual.Length

class RegisterViewModel : ViewModel() {

    private val _registrationError = MutableLiveData<String?>(null)
    val registrationError: LiveData<String?> = _registrationError

    private val _email = MutableLiveData("")
    val email: LiveData<String> = _email

    private val _password = MutableLiveData("")
    val password: LiveData<String> = _password

    fun onEmailChange(email: String) {
        _email.value = email
    }

    fun onPasswordChange(password: String) {
        _password.value = password
    }

    fun register(
        name: String,
        surname: String,
        age: String,
        bio: String,
        isAdmin: Boolean,
        isVet: Boolean,
        navigateToDoctorScreen: () -> Unit // Функция для перехода на экран DoctorScreen
    ) {
        val email = email.value ?: return
        val password = password.value ?: return

        if (isRegistrationDataValid(email, password, name, surname, age, bio)) {
            FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val user = FirebaseAuth.getInstance().currentUser
                        val db = Firebase.firestore
                        val userRef = db.collection("users").document(user?.uid ?: "")

                        val data = hashMapOf(
                            "email" to email,
                            "name" to name,
                            "surname" to surname,
                            "age" to age,
                            "bio" to bio,
                            "updatedAt" to FieldValue.serverTimestamp(),
                            "isAdmin" to false,
                            "isVet" to false
                        )

                        userRef.set(data)
                            .addOnSuccessListener {
                                // Registration successful, navigate to the desired screen
                                navigateToDoctorScreen.invoke()
                            }
                            .addOnFailureListener { exception ->

                                _registrationError.value = exception.message
                            }
                    } else {
                        _registrationError.value = "Handle registration error"
                    }
                }
        } else {
            _registrationError.value = "invalid registration data"
        }
    }

    private fun isRegistrationDataValid(
        email: String,
        password: String,
        name: String,
        surname: String,
        age: String,
        bio: String
    ): Boolean {
        if (email.isEmpty()) {
            _registrationError.value = "Введите адрес электронной почты"
            return false
        }

        if (password.isEmpty()) {
            _registrationError.value = "Введите пароль"
            return false
        }

        if (name.isEmpty()) {
            _registrationError.value = "Введите имя"
            return false
        }

        if (surname.isEmpty()) {
            _registrationError.value = "Введите фамилию"
            return false
        }

        // Дополнительные проверки данных, если необходимо

        return true
    }


}