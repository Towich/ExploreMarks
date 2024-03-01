package com.example.exploremarks.ui.screen.register

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.exploremarks.R
import com.example.exploremarks.navigation.Screen
import com.example.exploremarks.ui.screen.login.components.CustomActionButton
import com.example.exploremarks.ui.screen.login.components.CustomClickableText
import com.example.exploremarks.ui.screen.login.components.CustomCommonTextField
import com.example.exploremarks.ui.screen.login.components.CustomPasswordTextField
import com.example.exploremarks.ui.theme.ExploreMarksTheme

@Composable
fun RegisterScreen(
    navController: NavController
) {
    var inputEmail by remember { mutableStateOf("") }
    var inputPassword by remember { mutableStateOf("") }

    Scaffold(

    ) { scaffoldPadding ->
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
                    value = inputEmail,
                    placeholderText = "Email",
                    onValueChange = { newText ->
                        inputEmail = newText
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
                    title = "SIGN UP",
                    modifier = Modifier
                        .padding(top = 40.dp)
                ) {
                    navController.navigate(Screen.MapScreen.route) {
                        popUpTo(0)
                    }
                }

                CustomActionButton(
                    title = "CONTINUE AS GUEST",
                    modifier = Modifier
                        .padding(top = 30.dp)
                ) {
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
                        firstPartText = "Have an account? ",
                        secondPartText = "Sign In"
                    ) {
                        navController.navigate(Screen.LoginScreen.route)
                    }
                }
            }
        }

    }
}

@Preview(device = "spec:width=1080px,height=2000px", showSystemUi = true)
@Composable
fun Mi9TReservationPreview() {
    ExploreMarksTheme {
        RegisterScreen(rememberNavController())
    }
}

