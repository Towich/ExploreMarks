package com.example.exploremarks.ui.screen.map.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.exploremarks.R
import com.example.exploremarks.data.SessionMode
import com.example.exploremarks.data.model.MarkUIModel
import com.example.exploremarks.ui.screen.components.CustomActionButton
import com.example.exploremarks.ui.screen.components.CustomCommonTextField
import com.yandex.mapkit.geometry.Point
import java.util.UUID

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun NewMarkButtonSheet(
    sheetState: SheetState,
    sessionMode: SessionMode,
    geoPoint: Point,
    onCreateMark: (newMark: MarkUIModel) -> Unit,
    onDismissRequest: () -> Unit
) {
    var inputDescription by remember { mutableStateOf("") }

    ModalBottomSheet(
        onDismissRequest = {
            onDismissRequest()
        },
        sheetState = sheetState,
        containerColor = MaterialTheme.colorScheme.background,
        scrimColor = Color.Transparent
    ) {
        // Sheet content

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.5f)
                .padding(start = 20.dp, end = 20.dp, bottom = 20.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(
                    text = "New Mark",
                    color = MaterialTheme.colorScheme.secondary,
                    fontWeight = FontWeight.Bold,
                    fontSize = 28.sp
                )

                Row(
                    modifier = Modifier
                        .padding(top = 20.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Latitude",
                        color = MaterialTheme.colorScheme.secondary,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )
                    Text(
                        text = geoPoint.latitude.toString(),
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )
                }

                Row(
                    modifier = Modifier
                        .padding(top = 10.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Longitude",
                        color = MaterialTheme.colorScheme.secondary,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )
                    Text(
                        text = geoPoint.longitude.toString(),
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )
                }

                CustomCommonTextField(
                    value = inputDescription,
                    placeholderText = "Description",
                    onValueChange = { newText ->
                        inputDescription = newText
                    },
                    borderColor = MaterialTheme.colorScheme.primary,
                    textColor = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.padding(top = 20.dp)
                )

                CustomActionButton(
                    title = "CHOOSE IMAGE",
                    backgroundColor = MaterialTheme.colorScheme.secondary,
                    modifier = Modifier.padding(top = 20.dp)
                ) {
                    // TODO: onClick()
                }
            }

            CustomActionButton(
                title = "CREATE MARK",
                backgroundColor = MaterialTheme.colorScheme.primary
            ) {
                onCreateMark(
                    MarkUIModel(
                        id = UUID.randomUUID(),
                        latitude = geoPoint.latitude,
                        longitude = geoPoint.longitude,
                        description = inputDescription
                    )
                )
            }
        }

    }
}

//@OptIn(ExperimentalMaterial3Api::class)
//@Preview
//@Composable
//private fun Preview(){
//    val state = rememberModalBottomSheetState()
//    val scope = rememberCoroutineScope()
//
//    LaunchedEffect(key1 = null) {
//        state.expand()
//    }
//
//    MarkInfoButtonSheet(
//        sheetState = state,
//        chosenPoint = null,
//        onDismissRequest = {}
//    )
//}