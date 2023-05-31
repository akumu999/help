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

@Composable
fun DoctorPost(controller: NavController) {
    val db = Firebase.firestore
    val user = remember { FirebaseAuth.getInstance().currentUser }
    val postRef = db.collection("posts")

    var id by remember { mutableStateOf("") }
    var petType by remember { mutableStateOf("") }
    var petName by remember { mutableStateOf("") }
    var ownerName by remember { mutableStateOf("") }
    var report by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var status by remember { mutableStateOf("") }

    Column(modifier = Modifier
        .background(Bisque2)
        .fillMaxSize()
        .padding(16.dp)) {
        OutlinedTextField(
            value = petType,
            onValueChange = { newValue ->
                if (newValue.matches(Regex("^[a-zA-Zа-яА-Я]+$"))) {
                    petType = newValue
                }
            },
            label = { Text("Вид питомца") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = petName,
            onValueChange = { newValue ->
                if (newValue.matches(Regex("^[a-zA-Zа-яА-Я]+$"))) {
                    petName = newValue
                }
            },
            label = { Text("Имя питомца") },
            modifier = Modifier.fillMaxWidth()
        )
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
            modifier = Modifier
                .fillMaxWidth()
        )
        OutlinedTextField(
            value = phone,
            onValueChange = { phone = it },
            label = { Text("Номер телефона") },
            modifier = Modifier
                .fillMaxWidth()
        )

        Button( colors = ButtonDefaults.buttonColors(backgroundColor = Bisque4), elevation = ButtonDefaults.elevation(defaultElevation = 8.dp, pressedElevation = 16.dp),
            onClick = {
                val post = Post(id, petType, petName, ownerName, report, phone, status)
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
