import Post
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Green
import androidx.compose.ui.graphics.Color.Companion.Red
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.pethelper.Navigation.NavScreens
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObjects
import kotlinx.coroutines.tasks.await
import com.example.pethelper.ui.theme.Bisque2
import com.example.pethelper.ui.theme.Bisque4

@Composable
fun VScreen(controller: NavController) {
    val db = FirebaseFirestore.getInstance()
    var historyPosts by remember { mutableStateOf<List<Post>>(emptyList()) }

    // Хранит список всех постов
    var posts by remember { mutableStateOf<List<Post>>(emptyList()) }

    // Получаем список всех постов из Firestore
    LaunchedEffect(Unit) {
        val querySnapshot = db.collection("posts")
            .get()
            .await()
        val allPosts = querySnapshot.toObjects<Post>()
        historyPosts = allPosts.filter { post ->
            post.status == "Принята" || post.status == "Отклонена"
        }
        posts = allPosts.filter { post ->
            post.status != "Принята" && post.status != "Отклонена"
        }
    }


    // Фильтруем список заявок, оставляя только необработанные
    val unprocessedPosts = posts.filter { post ->
        post.status != "Принята" && post.status != "Отклонена"
    }

    Column(modifier = Modifier.fillMaxSize().background(Bisque2)) {
        Text(
            text = "Список заявок",
            style = MaterialTheme.typography.h6,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(16.dp).align(Alignment.CenterHorizontally)
        )

        // Кнопка "История заявок"
        Button(colors = ButtonDefaults.buttonColors(backgroundColor = Bisque4), elevation = ButtonDefaults.elevation(defaultElevation = 8.dp, pressedElevation = 16.dp),
            onClick = { controller.navigate(NavScreens.History.route) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(text = "История заявок", color = Color.White)
        }

        // Отображаем список необработанных постов в LazyColumn
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(unprocessedPosts) { post ->
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
                            // Проверяем статус заявки и отображаем соответствующий текст
                            if (post.status == "Принята") {
                                Text(
                                    text = "Статус: Принята",
                                    color = Color.Black,
                                    fontWeight = FontWeight.Bold
                                )
                            } else if (post.status == "Отклонена") {
                                Text(
                                    text = "Статус: Отклонена",
                                    color = Color.Black,
                                    fontWeight = FontWeight.Bold
                                )
                            } else {
                                // Заявка не обработана, отображаем кнопки принятия и отклонения
                                Button(colors = ButtonDefaults.buttonColors(backgroundColor = Color.Green), elevation = ButtonDefaults.elevation(defaultElevation = 8.dp, pressedElevation = 16.dp),
                                    onClick = { acceptPost(post) },
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Text(text = "Принять", color = Color.White)
                                }

                                Button(colors = ButtonDefaults.buttonColors(backgroundColor = Color.Red), elevation = ButtonDefaults.elevation(defaultElevation = 8.dp, pressedElevation = 16.dp),
                                    onClick = { declinePost(post) },
                                    modifier = Modifier.fillMaxWidth()
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
}
// Обработка принятия заявки
fun acceptPost(post: Post) {
    // Здесь вы можете добавить код для обновления статуса поста или выполнения других действий,
    // например, отправка уведомления пользователю о принятии его заявки.

    // Пример обновления статуса поста на "Принята"
    post.status = "Принята"

    // Пример выполнения других действий, например, отправка уведомления

    // Сохраняем обновленный пост в Firestore
    savePost(post)
}

// Обработка отклонения заявки
fun declinePost(post: Post) {
    // Здесь вы можете добавить код для обновления статуса поста или выполнения других действий,
    // например, отправка уведомления пользователю о отклонении его заявки.

    // Пример обновления статуса поста на "Отклонена"
    post.status = "Отклонена"

    // Пример выполнения других действий, например, отправка уведомления

    // Сохраняем обновленный пост в Firestore
    savePost(post)
}

// Сохранение обновленного поста в Firestore
private fun savePost(post: Post) {
    val db = FirebaseFirestore.getInstance()

    // Сохраняем обновленные данные поста в Firestore
    db.collection("posts")
        .document(post.id)
        .set(post)
        .addOnSuccessListener {
            // Успешно сохранено
        }
        .addOnFailureListener { exception ->
            // Обработка ошибки сохранения
        }
}