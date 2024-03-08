package com.example.exploremarks.ui.screen.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp

@Composable
fun CustomPasswordTextField(
    value: String,
    placeholderText: String,
    onValueChange: (newText: String) -> Unit,
    modifier: Modifier = Modifier
){
    var passwordVisible by rememberSaveable { mutableStateOf(false) }

    TextField(
        value = value,
        onValueChange = {
            onValueChange(it)
        },
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent,
            disabledContainerColor = Color.Transparent,
            errorContainerColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            focusedTextColor = MaterialTheme.colorScheme.primary,
            unfocusedTextColor = MaterialTheme.colorScheme.primary
        ),
        shape = RoundedCornerShape(30.dp),
        placeholder = {
            Text(
                text = placeholderText,
                color = Color.White
            )
        },
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
        trailingIcon = {
            IconButton(onClick = { passwordVisible = !passwordVisible }){
                Icon(
                    imageVector = Icons.Outlined.Lock,
                    contentDescription = if (passwordVisible) "Hide password" else "Show password",
                    tint = Color.White
                )
            }
        },
        modifier = modifier
            .fillMaxWidth()
            .border(
                width = 2.dp,
                color = Color.White,
                shape = RoundedCornerShape(30.dp)
            )
    )
}