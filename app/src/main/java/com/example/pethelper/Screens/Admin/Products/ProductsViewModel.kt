package com.example.pethelper.Screens.Admin.Products

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

data class Products(
    val id: String,
    val name: String,
    val cost: String
)

class ProductsViewModel : ViewModel() {
    private val _productsList = MutableLiveData<List<Products>>()
    val productsList: LiveData<List<Products>> = _productsList

    private val firestore: FirebaseFirestore = Firebase.firestore

    init {
        fetchProducts()
    }

    private fun fetchProducts() {
        firestore.collection("products")
            .get()
            .addOnSuccessListener { querySnapshot ->
                val products = mutableListOf<Products>()
                for (document in querySnapshot) {
                    val id = document.id
                    val name = document.getString("name") ?: ""
                    val cost = document.getString("cost") ?: ""
                    // Остальные поля продукта
                    products.add(Products(id, name, cost))
                }
                _productsList.value = products
            }
            .addOnFailureListener { exception ->
                // Handle error
            }
    }

    fun deleteProduct(product: Products) {
        firestore.collection("products")
            .document(product.id)
            .delete()
            .addOnSuccessListener {
                val updatedList = _productsList.value?.toMutableList()
                updatedList?.remove(product)
                _productsList.value = updatedList
            }
            .addOnFailureListener { exception ->
                // Обработка ошибки при удалении
            }
    }
}
