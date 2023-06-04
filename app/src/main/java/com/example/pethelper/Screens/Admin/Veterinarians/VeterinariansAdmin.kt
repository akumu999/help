import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.pethelper.Navigation.NavScreens
import com.example.pethelper.R
import com.example.pethelper.Screens.Admin.Products.Veterinarians
import com.example.pethelper.Screens.Admin.Products.VeterinariansViewModel
import com.example.pethelper.ui.theme.Bisque1
import com.example.pethelper.ui.theme.Bisque2
import com.example.pethelper.ui.theme.Bisque4


@Composable
fun VeterinariansAdmin(controller: NavController, viewModel: VeterinariansViewModel = viewModel()) {
    val veterinarians = viewModel.veterinariansList.value.orEmpty()

    // Fetch the veterinarians list when the composable is first displayed
    LaunchedEffect(true) {
        viewModel.fetchVeterinarians()
    }

    var searchQuery by remember { mutableStateOf("") }

    // Отфильтрованный список товаров
    val filteredVeterinarian= if (searchQuery.isEmpty()) {
        veterinarians
    } else {
        veterinarians.filter { it.name.contains(searchQuery, ignoreCase = true) }
    }

    Column(modifier = Modifier.background(Bisque2).fillMaxSize()) {
        Text(
            text = "Список ветеринаров",
            style = MaterialTheme.typography.h5,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(16.dp),
            color = Color.Black
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
        Button(
            onClick = { controller.navigate(NavScreens.VeterinariansAdd.route) },
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 8.dp)
                .fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(backgroundColor = Bisque4)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = null,
                    tint = Color.White
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = "Добавить Ветеринара", color = Color.White)
            }
        }

        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(filteredVeterinarian) { veterinarian ->
                VeterinariansItem(
                    veterinarians = veterinarian,
                    onEditClick = { controller.navigate("${NavScreens.EditVeterinarianScreen.route}/${veterinarian.id}") }
                )
            }
        }
    }
}


@Composable
fun VeterinariansItem(veterinarians: Veterinarians, onEditClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(text = veterinarians.surname + " " + veterinarians.name + " " + veterinarians.midname + "\n", fontWeight = FontWeight.Bold)
                Text(text = "Специальность:\n"+veterinarians.speciality+ "\n")
                Text(text = "Образование:\n"+veterinarians.education+ "\n")
                Text(text = "Опыт работы:\n"+veterinarians.work_experience+ "\n")
            }
            IconButton(
                onClick = onEditClick,
                modifier = Modifier.padding(end = 8.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "Edit Veterinarian",
                    tint = Color.Black
                )
            }
        }
    }
}