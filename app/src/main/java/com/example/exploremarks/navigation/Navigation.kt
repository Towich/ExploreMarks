package com.example.exploremarks.navigation

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.exploremarks.ui.screen.login.LoginScreen
import com.example.exploremarks.ui.screen.map.MapScreen
import com.example.exploremarks.ui.screen.register.RegisterScreen
import com.example.exploremarks.ui.screen.util.EnterAnimation

@Composable
fun Navigation(
    context: Context,
    navController: NavHostController
) {
    NavHost(navController = navController, startDestination = Screen.LoginScreen.route) {
        composable(
            route = Screen.LoginScreen.route + "?showSuccessfulRegistered={showSuccessfulRegistered}",
            arguments = listOf(navArgument("showSuccessfulRegistered") { defaultValue = false })
        ) {
            EnterAnimation {
                LoginScreen(
                    navController = navController,
                    showSuccessfulRegistered = it.arguments?.getBoolean("showSuccessfulRegistered")
                        ?: false
                )
            }
        }
        composable(route = Screen.RegisterScreen.route) {
            EnterAnimation {
                RegisterScreen(
                    navController = navController
                )
            }

        }
        composable(route = Screen.MapScreen.route) {
            EnterAnimation {
                MapScreen(
                    context = context
                )
            }
        }
    }
}