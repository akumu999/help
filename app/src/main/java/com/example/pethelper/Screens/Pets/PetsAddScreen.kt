import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.pethelper.ui.theme.Bisque2
import com.example.pethelper.ui.theme.Bisque4
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

@Composable
fun PetsAddScreen(controller: NavController) {
    val db = Firebase.firestore
    val user = remember { FirebaseAuth.getInstance().currentUser }
    val petRef = db.collection("users").document(user!!.uid).collection("pets")

    var type by remember { mutableStateOf("") }
    var name by remember { mutableStateOf("") }
    var breed by remember { mutableStateOf("") }
    var age by remember { mutableStateOf("") }
    var gender by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }

    Column(modifier = Modifier.background(Bisque2).fillMaxSize().padding(16.dp)) {
        OutlinedTextField(
            value = type,
            onValueChange = { type = it },
            label = { Text("Вид") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Имя") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = breed,
            onValueChange = { breed = it },
            label = { Text("Порода") },
            modifier = Modifier.fillMaxWidth().padding(top = 16.dp)
        )
        OutlinedTextField(
            value = age,
            onValueChange = { age = it },
            label = { Text("Возраст") },
            modifier = Modifier.fillMaxWidth().padding(top = 16.dp)
        )
        OutlinedTextField(
            value = gender,
            onValueChange = { gender = it },
            label = { Text("Пол") },
            modifier = Modifier.fillMaxWidth().padding(top = 16.dp)
        )
        OutlinedTextField(
            value = description,
            onValueChange = { description = it },
            label = { Text("Описание") },
            modifier = Modifier.fillMaxWidth().padding(top = 16.dp)
        )
        Button( colors = ButtonDefaults.buttonColors(backgroundColor = Bisque4), elevation = ButtonDefaults.elevation(defaultElevation = 8.dp, pressedElevation = 16.dp),
            onClick = {
                val pet = Pet(name, type, breed, age, gender, description)
                val petRef = petRef.document()
                val petId = petRef.id // Получаем новый ID для документа питомца
                petRef.set(pet.copy(id = petId)) // Сохраняем данные питомца в Firestore
                controller.popBackStack() // Возвращаемся к предыдущему экрану
            },

            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
        ) {
            Text("Добавить питомца", color = Color.White)
        }
    }
}

data class Pet(
    val id: String = "",
    val name: String = "",
    val type: String = "",
    val breed: String = "",
    val age: String = "",
    val gender: String = "",
    val description: String = ""
)
