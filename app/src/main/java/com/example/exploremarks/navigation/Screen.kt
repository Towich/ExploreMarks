package com.example.exploremarks.navigation

sealed class Screen(val route: String){
    data object LoginScreen: Screen(route = "login_screen")
    data object RegisterScreen: Screen(route = "register_screen")
    data object MapScreen: Screen(route = "map_screen")
}
