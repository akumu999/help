import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
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

    var id by remember { mutableStateOf("") }
    var type by remember { mutableStateOf("") }
    var name by remember { mutableStateOf("") }
    var breed by remember { mutableStateOf("") }
    var age by remember { mutableStateOf("") }
    var gender by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }

    Column(modifier = Modifier
        .background(Bisque2)
        .fillMaxSize()
        .padding(16.dp)) {
        OutlinedTextField(
            value = name,
            onValueChange = { newValue ->
                if (newValue.all { it.isLetter() }) {
                    name = newValue
                }
            },
            label = { Text(text = "Имя питомца") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )
        OutlinedTextField(
            value = type,
            onValueChange = { newValue ->
                if (newValue.all { it.isLetter() }) {
                    type = newValue
                }
            },
            label = { Text(text = "Вид") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )

        OutlinedTextField(
            value = breed,
            onValueChange = { newValue ->
                if (newValue.all { it.isLetter() }) {
                    breed = newValue
                }
            },
            label = { Text(text = "Порода") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )
        OutlinedTextField(
                value = gender,
        onValueChange = { gender = it },
        label = { Text("Пол") },
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp)
        )
        OutlinedTextField(
            value = age,
            onValueChange = { newValue ->
                if (newValue.all { it.isDigit() }) {
                    age = newValue
                }
            },
            label = { Text(text = "Age") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )

        OutlinedTextField(
            value = description,
            onValueChange = { description = it },
            label = { Text("Описание") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
        )
        Button(
            colors = ButtonDefaults.buttonColors(backgroundColor = Bisque4),
            elevation = ButtonDefaults.elevation(defaultElevation = 8.dp, pressedElevation = 16.dp),
            onClick = {
                val pet = Pet(id, name, type, breed, age, gender, description)
                val petId = petRef.document().id // Получаем новый ID для нового документа питомца
                petRef.document(petId).set(pet.copy(id = petId)) // Сохраняем данные питомца в новом документе в Firestore
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
