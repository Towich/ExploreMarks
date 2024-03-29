package com.example.exploremarks.ui.screen.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.unit.dp
import com.example.exploremarks.ui.theme.Blue
import com.example.exploremarks.ui.theme.YellowFF

@Composable
fun CustomCommonTextField(
    value: String,
    placeholderText: String,
    onValueChange: (newText: String) -> Unit,
    borderColor: Color = Color.White,
    textColor: Color = Color.White,
    modifier: Modifier = Modifier
){
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
                color = textColor
            )
        },
        singleLine = true,
        modifier = modifier
            .fillMaxWidth()
            .border(
                width = 2.dp,
                color = borderColor,
                shape = RoundedCornerShape(30.dp)
            )
    )
}