package com.example.jetgarage.ui.navigation

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Cart : Screen("cart")
    object Profile : Screen("profile")
    object DetailCar : Screen("home/{carId}") {
        fun createRoute(carId: Long) = "home/$carId"
    }
}