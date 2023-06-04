import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
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
    var chipNumber by remember { mutableStateOf("") }
    var chipDate by remember { mutableStateOf("") }
    var passportNumber by remember { mutableStateOf("") }

    val currentContext = LocalContext.current

    val petTypes = listOf("Кошка", "Собака")
    val genderOptions = listOf("Мужской", "Женский")
    var expandedType by remember { mutableStateOf(false) }
    var expandedGender by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .background(Bisque2)
            .fillMaxSize()
            .padding(16.dp)
    ) {
        OutlinedTextField(
            value = name,
            onValueChange = { newValue ->
                if (newValue.all { it.isLetter() }) {
                    name = newValue
                }
            },
            label = { Text(text = "Кличка питомца") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp)
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
                .padding(4.dp)
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
                .padding(4.dp)
        )

        OutlinedTextField(
            value = description,
            onValueChange = { description = it },
            label = { Text("Описание") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp)
        )
        OutlinedTextField(
            value = chipNumber,
            onValueChange = { newValue ->
                if (newValue.all { it.isDigit() }) {
                    chipNumber = newValue
                }
            },
            label = { Text(text = "Номер электронного чипа") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp)
        )
        OutlinedTextField(
            value = chipDate,
            onValueChange = { newValue ->
                if (newValue.all { it.isDigit() }) {
                    chipDate = newValue
                }
            },
            label = { Text(text = "Дата чипирования:") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp)
        )
        OutlinedTextField(
            value = passportNumber,
            onValueChange = { newValue ->
                if (newValue.all { it.isDigit() }) {
                    passportNumber = newValue
                }
            },
            label = { Text(text = "Номер паспорта питомца") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp)
        )
        Box(modifier = Modifier.fillMaxWidth()) {
            TextButton(
                onClick = { expandedType = true },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = if (type.isNotBlank()) type else "Выберите вид питомца",
                    style = MaterialTheme.typography.body1
                )
            }
            DropdownMenu(
                expanded = expandedType,
                onDismissRequest = { expandedType = false },
                modifier = Modifier.fillMaxWidth()
            ) {
                petTypes.forEach { petType ->
                    DropdownMenuItem(onClick = {
                        type = petType
                        expandedType = false
                    }) {
                        Text(text = petType)
                    }
                }
            }
        }

        Box(modifier = Modifier.fillMaxWidth()) {
            TextButton(
                onClick = { expandedGender = true },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = if (gender.isNotBlank()) gender else "Выберите пол питомца",
                    style = MaterialTheme.typography.body1
                )
            }
            DropdownMenu(
                expanded = expandedGender,
                onDismissRequest = { expandedGender = false },
                modifier = Modifier.fillMaxWidth()
            ) {
                genderOptions.forEach { option ->
                    DropdownMenuItem(onClick = {
                        gender = option
                        expandedGender = false
                    }) {
                        Text(text = option)
                    }
                }
            }
        }

        Button(
            colors = ButtonDefaults.buttonColors(backgroundColor = Bisque4),
            elevation = ButtonDefaults.elevation(defaultElevation = 8.dp, pressedElevation = 16.dp),
            onClick = {
                val pet = Pet(id, name, type, breed, age, gender, description, chipNumber, chipDate, passportNumber)
                val petId = petRef.document().id
                petRef.document(petId).set(pet.copy(id = petId))
                controller.popBackStack()
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
    val description: String = "",
    val chipNumber: String = "",
    val chipDate: String = "",
    val passportNumber: String = ""
)
