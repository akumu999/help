import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
    val petRef = db.collection("posts")

    var petName by remember { mutableStateOf("") }
    var ownerName by remember { mutableStateOf("") }
    var report by remember { mutableStateOf("") }
    var petType by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }

    Column(modifier = Modifier.background(Bisque2).fillMaxSize().padding(16.dp)) {
        OutlinedTextField(
            value = petType,
            onValueChange = { petType = it },
            label = { Text("Вид питомца") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = petName,
            onValueChange = { petName = it },
            label = { Text("Имя питомца") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = ownerName,
            onValueChange = { ownerName = it },
            label = { Text("Ваше имя") },
            modifier = Modifier.fillMaxWidth().padding(top = 16.dp)
        )
        OutlinedTextField(
            value = report,
            onValueChange = { report = it },
            label = { Text("Жалобы") },
            modifier = Modifier.fillMaxWidth().padding(top = 16.dp)
        )
        OutlinedTextField(
            value = phone,
            onValueChange = { phone = it },
            label = { Text("Ваш номер телефона") },
            modifier = Modifier.fillMaxWidth().padding(top = 16.dp)
        )

        Button( colors = ButtonDefaults.buttonColors(backgroundColor = Bisque4), elevation = ButtonDefaults.elevation(defaultElevation = 8.dp, pressedElevation = 16.dp),
            onClick = {
                val post = Post(petType, petName, petName, ownerName, report, phone)
                val postRef = petRef.document()
                val postId = petRef.id // Получаем новый ID для документа питомца
                postRef.set(post.copy(id = postId)) // Сохраняем данные питомца в Firestore
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

data class Post(
    val id: String = "",
    val petType: String = "",
    val petName: String = "",
    val ownerName: String = "",
    val report: String = "",
    val phone: String = ""
)
