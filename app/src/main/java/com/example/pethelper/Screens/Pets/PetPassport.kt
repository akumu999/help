import Pet
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.pethelper.ui.theme.Bisque1
import com.example.pethelper.ui.theme.Bisque2
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import kotlinx.coroutines.tasks.await

@Composable
fun PetPassport(controller: NavController, petId: String) {
    val db = FirebaseFirestore.getInstance()
    val user = FirebaseAuth.getInstance().currentUser

    // Хранит информацию о выбранном питомце
    var pet by remember { mutableStateOf<Pet?>(null) }

    // Получаем информацию о питомце из Firestore
    LaunchedEffect(petId) {
        val docSnapshot = db.collection("users")
            .document(user!!.uid)
            .collection("pets")
            .document(petId)
            .get()
            .await()
        pet = docSnapshot.toObject<Pet>()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Bisque2)
            .padding(16.dp),
        contentAlignment = Alignment.TopCenter
    ) {
        Text(text = "Паспорт питомца", fontWeight = FontWeight.Bold, fontSize = 32.sp)
        if (pet != null) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 50.dp),
                elevation = 4.dp,
                backgroundColor = Color.White
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp)
                ) {
                    Text(
                        text = "Имя: ${pet!!.name}",
                        fontWeight = FontWeight.Bold,
                        fontSize = 24.sp,
                        modifier = Modifier.padding(top = 16.dp, bottom = 8.dp)
                    )
                    Text(text = "Вид: ${pet!!.type}")
                    Text(text = "Порода: ${pet!!.breed}")
                    Text(text = "Возраст: ${pet!!.age}")
                    Text(text = "Пол: ${pet!!.gender}")
                    Text(text = "Описание: ${pet!!.description}")
                    Text(text = "Номер электронного чипа: ${pet!!.chipNumber}")
                    Text(text = "Дата чипирования: ${pet!!.chipDate}")
                    Text(text = "Номер паспорта питомца: ${pet!!.passportNumber}", modifier = Modifier.padding(bottom = 16.dp))
                }
            }
        } else {
            CircularProgressIndicator()
        }
    }
}
