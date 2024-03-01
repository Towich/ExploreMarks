package com.example.exploremarks.navigation

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.exploremarks.ui.screen.login.LoginScreen
import com.example.exploremarks.ui.screen.map.MapScreen
import com.example.exploremarks.ui.screen.register.RegisterScreen

@Composable
fun Navigation(
    context: Context,
    navController: NavHostController
){
    NavHost(navController = navController, startDestination = Screen.LoginScreen.route){
        composable(route = Screen.LoginScreen.route){
            LoginScreen(
                navController = navController
            )
        }
        composable(route = Screen.RegisterScreen.route){
            RegisterScreen(
                navController = navController
            )
        }
        composable(route = Screen.MapScreen.route){
            MapScreen(
                context = context
            )
        }
    }
}