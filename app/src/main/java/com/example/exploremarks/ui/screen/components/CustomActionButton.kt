package com.example.exploremarks.ui.screen.components

import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.exploremarks.navigation.Screen

@Composable
fun CustomActionButton(
    title: String,
    isLoading: Boolean = false,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
){
    Button(
        onClick = { onClick() },
        shape = RoundedCornerShape(30.dp),
        modifier = modifier
            .height(58.dp)
            .fillMaxWidth(),
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primary
        )
    ) {
        if(isLoading){
            CircularProgressIndicator(
                modifier = Modifier.fillMaxHeight(),
                color = MaterialTheme.colorScheme.primary,
                trackColor = MaterialTheme.colorScheme.secondary,
            )
        }
        else{
            Text(
                text = title,
                fontSize = 20.sp,
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
        }
    }
}