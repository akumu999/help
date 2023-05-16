package com.example.pethelper.Screens.Admin.Veterinarians.Add

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

data class Veterinarian(
    val id: String,
    val name: String,
    val surname: String,
    val midname: String,
    val education: String,
    val speciality: String,
    val work_experience: String
)

class ProductsViewModel : ViewModel() {
    private val _productsList = MutableLiveData<List<Veterinarian>>()
    val productsList: LiveData<List<Veterinarian>> = _productsList

    private val firestore: FirebaseFirestore = Firebase.firestore

    init {
        fetchProducts()
    }

    private fun fetchProducts() {
        firestore.collection("products")
            .get()
            .addOnSuccessListener { querySnapshot ->
                val products = mutableListOf<Veterinarian>()
                for (document in querySnapshot) {
                    val id = document.id
                    val name = document.getString("name") ?: ""
                    val surname = document.getString("surname") ?: ""
                    val midname = document.getString("midname") ?: ""
                    val education = document.getString("education") ?: ""
                    val speciality = document.getString("speciality") ?: ""
                    val work_experience = document.getString("work_experience") ?: ""
                    products.add(Veterinarian(id, name, surname, midname, education, speciality, work_experience))
                }
                _productsList.value = products
            }
            .addOnFailureListener { exception ->
                // Handle error
            }
    }
}
