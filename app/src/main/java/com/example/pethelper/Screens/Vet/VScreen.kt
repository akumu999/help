import Post
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Green
import androidx.compose.ui.graphics.Color.Companion.Red
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.pethelper.Navigation.NavScreens
import com.example.pethelper.R
import com.example.pethelper.ui.theme.*
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObjects
import kotlinx.coroutines.tasks.await

@Composable
fun VScreen(controller: NavController, context: Context) {
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

    var searchQuery by remember { mutableStateOf("") }

    // Отфильтрованный список товаров
    val filterPosts= if (searchQuery.isEmpty()) {
        posts
    } else {
        posts.filter { it.petName.contains(searchQuery, ignoreCase = true) }
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
        TopAppBar(
            title = { Text(text = "PETHELPER", textAlign = TextAlign.Center) },
            backgroundColor = Bisque1,
            elevation = 8.dp,
            actions = {
                // TextField для поиска
                TextField(
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    modifier = Modifier
                        .padding(end = 16.dp)
                        .fillMaxWidth()
                        .size(60.dp),
                    placeholder = { Text(text = "Поиск") },
                    colors = TextFieldDefaults.textFieldColors(
                        backgroundColor = Bisque1,
                        cursorColor = Color.Black,
                        focusedIndicatorColor = Bisque1,
                        unfocusedIndicatorColor = Bisque1,
                        focusedLabelColor = Color.Black,
                        unfocusedLabelColor = Color.Black
                    ),
                    leadingIcon = {
                        Icon(
                            painter = painterResource(id = R.drawable.search),
                            contentDescription = "Search"
                        )
                    }
                )
            }
        )
        // Кнопка "История заявок"
        Button(colors = ButtonDefaults.buttonColors(backgroundColor = Bisque4), elevation = ButtonDefaults.elevation(defaultElevation = 8.dp, pressedElevation = 16.dp),
            onClick = { controller.navigate(NavScreens.History.route) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(text = "История заявок", color = Color.White, fontSize = 20.sp)
        }

        // Отображаем список необработанных постов в LazyColumn
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(filterPosts) { post ->
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
                                Button(colors = ButtonDefaults.buttonColors(backgroundColor = GreenButton), elevation = ButtonDefaults.elevation(defaultElevation = 8.dp, pressedElevation = 16.dp),
                                    onClick = { acceptPost(post, context) },
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Text(text = "Принять", color = Color.White)
                                }

                                Button(colors = ButtonDefaults.buttonColors(backgroundColor = RedButton), elevation = ButtonDefaults.elevation(defaultElevation = 8.dp, pressedElevation = 16.dp),
                                    onClick = { declinePost(post, context) },
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
fun acceptPost(post: Post, context: Context) {
    // Здесь вы можете добавить код для обновления статуса поста или выполнения других действий,
    // например, отправка уведомления пользователю о принятии его заявки.
Toast.makeText(context, "Заявка принята", Toast.LENGTH_SHORT)
    // Пример обновления статуса поста на "Принята"
    post.status = "Принята"

    // Пример выполнения других действий, например, отправка уведомления

    // Сохраняем обновленный пост в Firestore
    savePost(post)
}

// Обработка отклонения заявки
fun declinePost(post: Post, context: Context) {
    // Здесь вы можете добавить код для обновления статуса поста или выполнения других действий,
    // например, отправка уведомления пользователю о отклонении его заявки.
    Toast.makeText(context, "Заявка отклонена", Toast.LENGTH_SHORT)
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