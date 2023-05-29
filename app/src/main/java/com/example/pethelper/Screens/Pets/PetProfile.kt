import androidx.compose.runtime.*
import Pet
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.pethelper.Navigation.NavScreens
import com.example.pethelper.ui.theme.Bisque2
import com.example.pethelper.ui.theme.Bisque4
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await


@Composable
fun PetProfile(petId: String, controller: NavController) {
    var id by remember { mutableStateOf("") }
    var name by remember { mutableStateOf("") }
    var type by remember { mutableStateOf("") }
    var breed by remember { mutableStateOf("") }
    var gender by remember { mutableStateOf("") }
    var age by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }


    val db = Firebase.firestore
    val user = FirebaseAuth.getInstance().currentUser
    val petRef = db.collection("users").document(user!!.uid).collection("pets").document(petId)



    LaunchedEffect(Unit) {
        // Получите данные о питомце из Firebase Firestore
        val petSnapshot = petRef.get().await()
        if (petSnapshot.exists()) {
            // Если документ существует, получите данные
            val petData = petSnapshot.data
            id = petData?.get("id") as? String ?: ""
            name = petData?.get("name") as? String ?: ""
            type = petData?.get("type") as? String ?: ""
            breed = petData?.get("breed") as? String ?: ""
            gender = petData?.get("gender") as? String ?: ""
            age = petData?.get("age") as? String ?: ""
            description = petData?.get("description") as? String ?: ""
        }
    }


    Column(
        modifier = Modifier.background(Bisque2)
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Редактирование информации о питомце",
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )
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
            onValueChange = { newValue ->
                if (newValue.all { it.isLetter() }) {
                    gender = newValue
                }
            },
            label = { Text(text = "Пол") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )
        OutlinedTextField(
            value = age,
            onValueChange = { newValue ->
                if (newValue.all { it.isDigit() }) {
                    age = newValue
                }
            },
            label = { Text(text = "Возраст") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )
        OutlinedTextField(
            value = description,
            onValueChange = { description = it },
            label = { Text("Описание") },
                modifier = Modifier.padding(bottom = 8.dp)
                )
                Button(colors = ButtonDefaults.buttonColors(backgroundColor = Bisque4), elevation = ButtonDefaults.elevation(defaultElevation = 8.dp, pressedElevation = 16.dp),
                    onClick = {
                        val petData = hashMapOf(
                            "id" to id,
                            "name" to name,
                            "type" to type,
                            "breed" to breed,
                            "gender" to gender,
                            "age" to age,
                            "description" to description
                        )
                        petRef.set(petData)
                        controller.navigate(NavScreens.PetsScreen.route)
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = "Сохранить изменения", color = Color.White) }
                Button(colors = ButtonDefaults.buttonColors(backgroundColor = Bisque4), elevation = ButtonDefaults.elevation(defaultElevation = 8.dp, pressedElevation = 16.dp), onClick = { petRef.delete().addOnCompleteListener{
                    task -> if(task.isSuccessful){
                        controller.navigate(NavScreens.PetsScreen.route)
                } else {
                    
                }
                }}, modifier = Modifier.fillMaxWidth()) {
                    Text(text = "Удалить питомца", color = Color.White)
                }
    }
}

