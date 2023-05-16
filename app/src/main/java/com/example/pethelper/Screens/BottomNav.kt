package com.example.pethelper.Screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.pethelper.Navigation.NavScreens
import com.example.pethelper.R
import com.example.pethelper.ui.theme.Bisque1
import com.example.pethelper.ui.theme.Bisque4

@Composable
fun BottomNav(controller: NavController){

    BottomNavigation(
        backgroundColor = Bisque1, contentColor = Color.Black, elevation = -100.dp) {
        BottomNavigationItem(
            selected = true,
            onClick = { controller.navigate(NavScreens.DoctorScreen.route)},
            icon = { Icon(painter = painterResource(id = R.drawable.settings),
                contentDescription = "Записаться на прием" , modifier = Modifier.size(28.dp)) },
            label = { Text("На приём") }
        )

        BottomNavigationItem(
            selected = false,
            onClick = { controller.navigate(NavScreens.CatalogScreen.route) },
            icon = { Icon(painter = painterResource(id = R.drawable.catalog), contentDescription = "Каталог товаров", modifier = Modifier.size(28.dp)) },
            label = { Text("Услуги") }
        )

        BottomNavigationItem(
            selected = false,
            onClick = { controller.navigate(NavScreens.PetsScreen.route) },
            icon = { Icon(painter = painterResource(id = R.drawable.peticon), contentDescription = "Мои питомцы", modifier = Modifier.size(28.dp)) },
            label = { Text("Питомцы") }
        )

        BottomNavigationItem(
            selected = false,
            onClick = { controller.navigate(NavScreens.ProfileScreen.route) },
            icon = { Icon(painter = painterResource(id = R.drawable.user), contentDescription = "Профиль", modifier = Modifier.size(28.dp)) },
            label = { Text("Профиль") }
        )
    }
}