package com.example.pethelper.Navigation

import Pet
import PetProfile
import PetsAddScreen
import ProductsAdmin
import VeterinariansAdmin
import android.content.Context
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.NavHostController
import com.example.pethelper.Screens.*
import com.example.pethelper.Screens.Admin.AdminMainScreen
import com.example.pethelper.Screens.Admin.Products.ProductsAdd
import com.example.pethelper.Screens.Admin.UsersAdmin
import com.example.pethelper.Screens.Catalog.ProductInfo
import com.example.pethelper.Screens.Doctors.Veterinarian
import com.example.pethelper.Screens.Login.LoginViewModel
import com.example.pethelper.Screens.Profile.ProfileScreen
import com.example.pethelper.Screens.Register.RegisterScreen
import com.example.pethelper.Screens.Register.RegisterViewModel


@Composable
fun SetNavController(controller: NavHostController, context : Context) {
    NavHost(navController = controller, startDestination = NavScreens.StartScreen.route) {
        composable(NavScreens.StartScreen.route) {
            StartScreen(controller)
        }
        composable(NavScreens.LoginScreen.route) {
            val loginViewModel = viewModel<LoginViewModel>()
            LoginScreen(viewModel = loginViewModel, controller, context)
        }
        composable(NavScreens.DoctorScreen.route) {
            DoctorScreen(controller)
        }
        composable(NavScreens.RegisterScreen.route) {
            val authViewModel = viewModel<RegisterViewModel>()
            RegisterScreen(viewModel = authViewModel, controller)
        }
        composable(NavScreens.CatalogScreen.route) {
            CatalogScreen(controller)
        }
        composable(NavScreens.PetsScreen.route) {
            PetsScreen(controller)
        }

        composable(NavScreens.PetProfile.route + "/{pet.id}") { backStackEntry ->
            PetProfile(petId = backStackEntry.arguments?.getString("pet.id") ?: "", controller = controller)
        }

        composable(NavScreens.PetsAddScreen.route) {
            PetsAddScreen(controller)
        }
        composable(NavScreens.ProfileScreen.route) {
            ProfileScreen(controller)
        }
        composable(NavScreens.ProfileEditScreen.route) {
            ProfileEditScreen(controller)
        }
        composable(NavScreens.ProductInfo.route) {
            ProductInfo(controller)
        }
        composable(NavScreens.AdminMainScreen.route) {
            AdminMainScreen(controller)
        }
        composable(NavScreens.ProductsAdmin.route) {
            ProductsAdmin(controller)
        }
        composable(NavScreens.UsersAdmin.route) {
            UsersAdmin()
        }
        composable(NavScreens.VeterinariansAdmin.route) {
            VeterinariansAdmin(controller)
        }
        composable(NavScreens.ProductsAdd.route) {
            ProductsAdd(controller)
        }
    }
}


