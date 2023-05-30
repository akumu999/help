package com.example.pethelper.Screens.Admin.Products

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.pethelper.Screens.Admin.Veterinarians.Add.Veterinarian
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

data class Veterinarians(
    val id: String,
    val name: String,
    val surname: String,
    val midname: String,
    val education: String,
    val speciality: String,
    val work_experience: String,
)

class VeterinariansViewModel : ViewModel() {
    private val _veterinariansList = MutableLiveData<List<Veterinarians>>()
    val veterinariansList: LiveData<List<Veterinarians>> = _veterinariansList

    private val firestore: FirebaseFirestore = Firebase.firestore

    init {
        fetchVeterinarians()
    }

     fun fetchVeterinarians() {
        firestore.collection("veterinarians")
            .get()
            .addOnSuccessListener { querySnapshot ->
                val veterinarians = mutableListOf<Veterinarians>()
                for (document in querySnapshot) {
                    val id = document.id
                    val name = document.getString("name") ?: ""
                    val surname = document.getString("surname") ?: ""
                    val midname = document.getString("midname") ?: ""
                    val education = document.getString("education") ?: ""
                    val speciality = document.getString("speciality") ?: ""
                    val work_experience = document.getString("work_experience") ?: ""
                    // Остальные поля продукта
                    veterinarians.add(Veterinarians(id, name, surname, midname, education, speciality, work_experience))
                }
                _veterinariansList.value = veterinarians
                fetchVeterinarians()
            }
            .addOnFailureListener { exception ->
                fetchVeterinarians()
            }
    }

    fun deleteVeterinarian(veterinarians: Veterinarians) {
        firestore.collection("veterinarians")
            .document(veterinarians.id)
            .delete()
            .addOnSuccessListener {
                val updatedList = _veterinariansList.value?.toMutableList()
                updatedList?.remove(veterinarians)
                _veterinariansList.value = updatedList
                fetchVeterinarians()
            }
            .addOnFailureListener { exception ->
                fetchVeterinarians()
            }
    }
}
