import androidx.compose.runtime.*
import Pet
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.pethelper.Navigation.NavScreens
import com.example.pethelper.ui.theme.Bisque2
import com.example.pethelper.ui.theme.Bisque4
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await


@Composable
fun EditProductScreen(productId: String, controller: NavController) {
    val db = Firebase.firestore
    val productRef = db.collection("products").document(productId)

    var name by remember { mutableStateOf("") }
    var cost by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        // Получите данные о продукте из Firebase Firestore
        val productSnapshot = productRef.get().await()
        if (productSnapshot.exists()) {
            // Если документ существует, получите данные
            val productData = productSnapshot.data
            name = productData?.get("name") as? String ?: ""
            cost = productData?.get("cost") as? String ?: ""
        }
    }

    Column(
        modifier = Modifier.background(Bisque2)
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Редактирование продукта",
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        OutlinedTextField(
            value = name,
            onValueChange = { newValue ->
                name = newValue
            },
            label = { Text(text = "Название продукта") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )
        OutlinedTextField(
            value = cost,
            onValueChange = { newValue ->
                cost = newValue
            },
            label = { Text(text = "Цена") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )
        Button(
            colors = ButtonDefaults.buttonColors(backgroundColor = Bisque4),
            elevation = ButtonDefaults.elevation(defaultElevation = 8.dp, pressedElevation = 16.dp),
            onClick = {
                val productData = hashMapOf(
                    "name" to name,
                    "cost" to cost
                )
                productRef.set(productData)
                controller.navigate(NavScreens.ProductsAdmin.route)
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Сохранить изменения", color = Color.White)
        }
    }
}
