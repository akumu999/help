import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.pethelper.Navigation.NavScreens
import com.example.pethelper.Screens.Admin.Products.Veterinarians
import com.example.pethelper.Screens.Admin.Products.VeterinariansViewModel
import com.example.pethelper.ui.theme.Bisque2
import com.example.pethelper.ui.theme.Bisque4


@Composable
fun VeterinariansAdmin(controller: NavController, viewModel: VeterinariansViewModel = viewModel()) {
    val veterinarians = viewModel.veterinariansList.value.orEmpty()
    Column(modifier = Modifier.background(Bisque2).fillMaxSize()) {
        Text(
            text = "Список ветеринаров",
            style = MaterialTheme.typography.h5,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(16.dp),
            color = Color.Black
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
            items(veterinarians) { veterinarian ->
                VeterinariansItem(
                    veterinarians = veterinarian,
                    onDeleteClick = { viewModel.deleteVeterinarian(veterinarian) },
                    onEditClick = { controller.navigate("${NavScreens.EditVeterinarianScreen.route}/${veterinarian.id}") }
                )
            }
        }
    }
}

@Composable
fun VeterinariansItem(veterinarians: Veterinarians, onDeleteClick: () -> Unit, onEditClick: () -> Unit) {
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
                onClick = { onEditClick() },
                modifier = Modifier.padding(end = 8.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "Edit Veterinarian",
                    tint = Color.Black
                )
            }
            IconButton(
                onClick = onDeleteClick,
                modifier = Modifier.padding(end = 8.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Delete Veterinarian",
                    tint = Color.Black
                )
            }
        }
    }
}