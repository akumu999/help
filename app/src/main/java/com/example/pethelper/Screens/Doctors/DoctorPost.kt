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
import com.example.pethelper.Navigation.NavScreens
import com.example.pethelper.ui.theme.Bisque2
import com.example.pethelper.ui.theme.Bisque4
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

@Composable
fun DoctorPost(controller: NavController) {
    val db = Firebase.firestore
    val user = remember { FirebaseAuth.getInstance().currentUser }
    val postRef = db.collection("posts")

    val petList by remember { mutableStateOf(mutableListOf<String>()) }
    val selectedPets = remember { mutableStateListOf<String>() }

    var id by remember { mutableStateOf("") }
    var petType by remember { mutableStateOf("") }
    var petName by remember { mutableStateOf("") }
    var ownerName by remember { mutableStateOf("") }
    var report by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var status by remember { mutableStateOf("") }

    // Загрузка списка питомцев из базы данных
    LaunchedEffect(Unit) {
        val snapshot = db.collection("users")
            .document(user!!.uid)
            .collection("pets")
            .get()
            .await()
        petList.clear()
        for (doc in snapshot.documents) {
            val petName = doc.getString("name") ?: ""
            val petType = doc.getString("type") ?: ""
            petList.add(petName)
        }
    }

    var expanded by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .background(Bisque2)
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Всплывающий список с выбором питомцев
        Box(modifier = Modifier.fillMaxWidth()) {
            TextButton(
                onClick = { expanded = true },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = if (selectedPets.isNotEmpty()) {
                        selectedPets.joinToString(", ")
                    } else {
                        "Выберите питомцев"
                    },
                    style = MaterialTheme.typography.body1
                )
            }
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier.fillMaxWidth()
            ) {
                petList.forEach { pet ->
                    DropdownMenuItem(onClick = {
                        if (selectedPets.contains(pet)) {
                            selectedPets.remove(pet)
                        } else {
                            selectedPets.add(pet)
                        }
                    }) {
                        Text(text = pet)
                    }
                }
            }
        }
        OutlinedTextField(
            value = ownerName,
            onValueChange = { newValue ->
                if (newValue.matches(Regex("^[a-zA-Zа-яА-Я]+$"))) {
                    ownerName = newValue
                }
            },
            label = { Text("Ваше имя") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = report,
            onValueChange = { report = it },
            label = { Text("Жалобы") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = phone,
            onValueChange = { phone = it },
            label = { Text("Номер телефона") },
            modifier = Modifier.fillMaxWidth()
        )

        Button(
            colors = ButtonDefaults.buttonColors(backgroundColor = Bisque4),
            elevation = ButtonDefaults.elevation(defaultElevation = 8.dp, pressedElevation = 16.dp),
            onClick = {
                val post = Post(
                    id = id,
                    petType = petType,
                    petName = selectedPets.joinToString(", "),
                    ownerName = ownerName,
                    report = report,
                    phone = phone,
                    status = status
                )
                val postRef = postRef.document()
                val postId = postRef.id // Получаем новый ID для документа питомца
                postRef.set(post.copy(id = postId)) // Сохраняем данные питомца в Firestore
                controller.popBackStack() // Возвращаемся к предыдущему экрану
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
        ) {
            Text("Отправить заявку на прием", color = Color.White)
        }
    }
}


data class Post(
    val id: String = "",
    val petType: String = "",
    val petName: String = "",
    val ownerName: String = "",
    val report: String = "",
    val phone: String = "",
    var status: String = ""
)
