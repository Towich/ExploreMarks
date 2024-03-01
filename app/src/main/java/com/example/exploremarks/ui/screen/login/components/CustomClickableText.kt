package com.example.exploremarks.ui.screen.login.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.exploremarks.navigation.Screen

@Composable
fun CustomClickableText(
    firstPartText: String,
    secondPartText: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
){
    val annotatedText = buildAnnotatedString {
        withStyle(
            style = SpanStyle(
                color = MaterialTheme.colorScheme.secondary,
            )
        ) {
            append(firstPartText)
        }

        withStyle(
            style = SpanStyle(
                color = MaterialTheme.colorScheme.primary,
            )
        ) {
            append(secondPartText)
        }
    }

    Text(
        text = annotatedText,
        fontSize = 16.sp,
        color = MaterialTheme.colorScheme.secondary,
        fontWeight = FontWeight.Bold,
        textAlign = TextAlign.Center,
        modifier = modifier
            .clickable {
                onClick()
            }
    )
}