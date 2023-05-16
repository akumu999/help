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
import com.example.pethelper.Screens.Admin.Products.Products
import com.example.pethelper.Screens.Admin.Products.ProductsViewModel
import com.example.pethelper.Screens.Admin.Veterinarian
import com.example.pethelper.Screens.Admin.VeterinariansViewModel


@Composable
fun ProductsAdmin(controller: NavController, viewModel: ProductsViewModel = viewModel()) {
    val products = viewModel.productsList.value.orEmpty()
    Column(modifier = Modifier.fillMaxSize()) {
        Text(
            text = "Список услуг",
            style = MaterialTheme.typography.h5,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(16.dp)
        )

        Button(
            onClick = { controller.navigate(NavScreens.ProductsAdd.route) },
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 8.dp)
                .fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(backgroundColor = Color.Green)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = null,
                    tint = Color.White
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = "Добавить услугу", color = Color.White)
            }
        }

        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(products) { product ->
                ProductsItem(product)
            }
        }
    }
}

@Composable
fun ProductsItem(products: Products) {
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
                Text(text = products.name, style = MaterialTheme.typography.h6)
            }
            IconButton(
                onClick = { /* Handle edit veterinarian */ },
                modifier = Modifier.padding(end = 8.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "Edit Veterinarian",
                    tint = Color.Black
                )
            }
            IconButton(
                onClick = { /* Handle delete veterinarian */ },
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
