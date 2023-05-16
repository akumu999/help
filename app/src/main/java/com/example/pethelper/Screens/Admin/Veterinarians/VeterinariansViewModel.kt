package com.example.pethelper.Screens.Admin

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

data class Veterinarian(
    val id: String,
    val name: String,
    val specialization: String
)

class VeterinariansViewModel : ViewModel() {
    private val _veterinariansList = MutableLiveData<List<Veterinarian>>()
    val veterinariansList: LiveData<List<Veterinarian>> = _veterinariansList

    private val firestore: FirebaseFirestore = Firebase.firestore

    init {
        fetchVeterinarians()
    }

    fun fetchVeterinarians() {
        firestore.collection("veterinarians")
            .get()
            .addOnSuccessListener { querySnapshot ->
                val veterinarians = mutableListOf<Veterinarian>()
                for (document in querySnapshot) {
                    val id = document.id
                    val name = document.getString("name") ?: ""
                    val specialization = document.getString("specialization") ?: ""
                    veterinarians.add(Veterinarian(id, name, specialization))
                }
                _veterinariansList.value = veterinarians
            }
            .addOnFailureListener { exception ->
                // Handle error
            }
    }
    fun deleteVeterinarian(veterinarian: Veterinarian) {
        firestore.collection("veterinarians")
            .document(veterinarian.id)
            .delete()
            .addOnSuccessListener {
                // Успешно удалено
            }
            .addOnFailureListener { exception ->
                // Обработка ошибки при удалении
            }
    }

}
