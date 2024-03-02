package com.example.exploremarks.ui.screen.login

import android.app.Activity
import android.content.Context
import android.view.inputmethod.InputMethodManager
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.getSystemService
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.exploremarks.R
import com.example.exploremarks.navigation.Screen
import com.example.exploremarks.ui.screen.login.components.CustomActionButton
import com.example.exploremarks.ui.screen.login.components.CustomClickableText
import com.example.exploremarks.ui.screen.login.components.CustomCommonTextField
import com.example.exploremarks.ui.screen.login.components.CustomPasswordTextField
import com.example.exploremarks.ui.theme.ExploreMarksTheme
import com.example.exploremarks.ui.viewmodel.LoginViewModel

@Composable
fun LoginScreen(
    navController: NavController,
    viewModel: LoginViewModel = hiltViewModel()
) {
    var inputUsername by remember { mutableStateOf("") }
    var inputPassword by remember { mutableStateOf("") }
    val uiState by viewModel.uiState.collectAsState()

    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    val keyboardController = LocalSoftwareKeyboardController.current

    when(uiState){
        is LoginScreenUiState.Error -> {
            inputUsername = ""
            inputPassword = ""
            LaunchedEffect(key1 = "key1") {
                snackbarHostState.showSnackbar((uiState as LoginScreenUiState.Error).message)
                viewModel.changeUiState(LoginScreenUiState.Initial)
            }
        }
        is LoginScreenUiState.Success -> {
            navController.navigate(Screen.MapScreen.route) {
                popUpTo(0)
            }
        }
        else -> {}
    }


    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        }
    )
    { scaffoldPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.login_register_screen_background),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxSize(),
                contentScale = ContentScale.FillHeight
            )
            Column(
                modifier = Modifier
                    .padding(scaffoldPadding)
                    .padding(start = 20.dp, end = 20.dp)
                    .fillMaxWidth()
                    .background(
                        color = MaterialTheme.colorScheme.background.copy(alpha = 0.8f),
                        shape = RoundedCornerShape(20.dp)
                    )
                    .padding(start = 15.dp, end = 15.dp, top = 50.dp, bottom = 50.dp),
                verticalArrangement = Arrangement.Top
            ) {
                CustomCommonTextField(
                    value = inputUsername,
                    placeholderText = "Username",
                    onValueChange = { newText ->
                        inputUsername = newText
                    }
                )
                CustomPasswordTextField(
                    value = inputPassword,
                    placeholderText = "Password",
                    onValueChange = { newText ->
                        inputPassword = newText
                    },
                    modifier = Modifier
                        .padding(top = 20.dp)
                )

                CustomActionButton(
                    title = "SIGN IN",
                    isLoading = uiState == LoginScreenUiState.Loading,
                    modifier = Modifier
                        .padding(top = 40.dp)
                ) {
                    keyboardController?.hide()
                    viewModel.performLogin(username = inputUsername, password = inputPassword)
                }

                CustomActionButton(
                    title = "CONTINUE AS GUEST",
                    modifier = Modifier
                        .padding(top = 30.dp)
                ) {
                    keyboardController?.hide()
//                    inputUsername = ""
//                    inputPassword = ""
                    navController.navigate(Screen.MapScreen.route) {
                        popUpTo(0)
                    }
                }

                Row(
                    modifier = Modifier
                        .padding(top = 20.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    CustomClickableText(
                        firstPartText = "Don't have an account? ",
                        secondPartText = "Sign Up"
                    ) {
                        navController.navigate(Screen.RegisterScreen.route)
                    }
                }
            }
        }

    }
}

//@Preview(device = "spec:width=1080px,height=2000px", showSystemUi = true)
//@Composable
//fun Mi9TReservationPreview() {
//    ExploreMarksTheme {
//        LoginScreen(rememberNavController())
//    }
//}

