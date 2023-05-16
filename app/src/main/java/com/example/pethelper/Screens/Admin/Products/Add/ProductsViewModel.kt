package com.example.pethelper.Screens.Admin.Products.Add

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.example.pethelper.Screens.Admin.Products.Add.Product

data class Product(
    val name: String,
    val cost: String
)
class ProductsViewModel : ViewModel() {
    private val firestore = FirebaseFirestore.getInstance()
    private val productsCollection = firestore.collection("products")

    val productsList: MutableLiveData<List<Product>> = MutableLiveData()

    fun addProduct(product: Product) {
        productsCollection.add(product)
            .addOnSuccessListener {
                // Successfully added
            }
            .addOnFailureListener { exception ->
                // Handle failure
            }
    }

    // Other methods for working with products (e.g., retrieving a list of products from Firebase)
    // Другие методы для работы с услугами (например, получение списка услуг из Firebase)
}
