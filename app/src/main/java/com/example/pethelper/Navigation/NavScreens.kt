package com.example.pethelper.Navigation


sealed class NavScreens (val route: String){

    object StartScreen : NavScreens("StartScreen")
    object LoginScreen : NavScreens("LoginScreen")
    object RegisterScreen : NavScreens("RegisterScreen")
    object DoctorScreen : NavScreens("DoctorScreen")
    object CatalogScreen : NavScreens("CatalogScreen")
    object PetsScreen : NavScreens("PetsScreen")
    object PetsAddScreen : NavScreens("PetsAddScreen")
    object ProfileScreen : NavScreens("ProfileScreen")
    object ProfileEditScreen : NavScreens("EditProfile")
    object PetProfile : NavScreens("PetProfile")
    object ProductInfo : NavScreens("ProductInfo")
    object AdminMainScreen : NavScreens("AdminMainScreen")
    object ProductsAdmin : NavScreens("ProductsAdmin")
    object UsersAdmin : NavScreens("UsersAdmin")
    object VeterinariansAdmin : NavScreens("VeterinarianAdmin")
    object ProductsAdd : NavScreens("ProductsAdd")
    object VeterinariansAdd : NavScreens("VeterinariansAdd")
}