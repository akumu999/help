import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.pethelper.ui.theme.Bisque2
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObjects
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

@Composable
fun History(controller: NavController) {
    val db = FirebaseFirestore.getInstance()
    val posts = remember { mutableStateListOf<Post>() }

    // Получаем список заявок из Firestore
    LaunchedEffect(Unit) {
        val querySnapshot = withContext(Dispatchers.IO) {
            db.collection("posts")
                .get()
                .await()
        }
        val fetchedPosts = querySnapshot.toObjects<Post>()
        posts.addAll(fetchedPosts)
    }

    Column(modifier = Modifier.fillMaxSize().background(Bisque2)) {
        Text(
            text = "История заявок",
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(16.dp)
        )

        // Отображаем список принятых и отклоненных заявок в Card
        posts.filter { post ->
            post.status == "Принята" || post.status == "Отклонена"
        }.forEach { post ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(text = "Вид животного: ${post.petType}")
                    Text(text = "Кличка: ${post.petName}")
                    Text(text = "Имя владельца: ${post.ownerName}")
                    Text(text = "Жалобы: ${post.report}")
                    Text(text = "Номер телефона владельца: ${post.phone}")
                    Text(text = "Статус: ${post.status}", fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}
