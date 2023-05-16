import Post
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Green
import androidx.compose.ui.graphics.Color.Companion.Red
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObjects
import kotlinx.coroutines.tasks.await
import com.example.pethelper.ui.theme.Bisque2

@Composable
fun VScreen(controller: NavController) {
    val db = FirebaseFirestore.getInstance()

    // Хранит список всех постов
    var posts by remember { mutableStateOf<List<Post>>(emptyList()) }

    // Получаем список всех постов из Firestore
    LaunchedEffect(Unit) {
        val querySnapshot = db.collection("posts")
            .get()
            .await()
        posts = querySnapshot.toObjects<Post>()
    }

    Column(modifier = Modifier.fillMaxSize().background(Bisque2)) {
        Text(
            text = "Список заявок",
            style = MaterialTheme.typography.h6,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(16.dp).align(Alignment.CenterHorizontally)
        )

        // Отображаем список постов в LazyColumn
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(posts) { post ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp, horizontal = 16.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 16.dp, horizontal = 16.dp)
                    ) {
                        Text(text = "Вид животного:\n" + post.petType + "\n")
                        Text(text = "Кличка:\n" + post.petName + "\n")
                        Text(text = "Имя владельца:\n" + post.ownerName + "\n")
                        Text(text = "Жалобы:\n" + post.report + "\n")
                        Text(text = "Номер телефона владельца:\n" + post.ownerName + "\n")

                        Column(modifier = Modifier.padding(top = 8.dp)) {
                            Button(
                                onClick = { /* Обработка принятия заявки */ },
                                modifier = Modifier
                                    .fillMaxWidth()
                            ) {
                                Text(text = "Принять", color = Color.White)
                            }

                            Button(
                                onClick = { /* Обработка отклонения заявки */ },
                                modifier = Modifier
                                    .fillMaxWidth()
                            ) {
                                Text(text = "Отклонить", color = Color.White)
                            }
                        }
                    }
                }
            }
        }
    }
}
