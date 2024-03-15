package com.example.exploremarks.ui.screen.map.components

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.exploremarks.data.model.MarkUIModel
import com.example.exploremarks.data.util.format
import com.example.exploremarks.ui.screen.components.CustomActionButton
import com.example.exploremarks.ui.screen.components.CustomCommonTextField
import com.yandex.mapkit.geometry.Point
import java.util.UUID

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewMarkBottomSheet(
    sheetState: SheetState,
    chosenImageBitmap: ImageBitmap?,
    geoPoint: Point,
    onCreateMark: (newMark: MarkUIModel) -> Unit,
    onImageChosen: (uri: Uri?) -> ImageBitmap?,
    onImageRemove: () -> Unit,
    onDismissRequest: () -> Unit
) {
    val galleryLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            if (uri != null) onImageChosen(uri)
        }

    ModalBottomSheet(
        onDismissRequest = {
            onDismissRequest()
        },
        sheetState = sheetState,
        containerColor = MaterialTheme.colorScheme.background,
        scrimColor = Color.Transparent,
        dragHandle = { BottomSheetDefaults.DragHandle() },
        windowInsets = WindowInsets.ime
    ) {
        // Sheet content

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.5f)
                .padding(start = 20.dp, end = 20.dp, bottom = 20.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            var inputDescription by remember { mutableStateOf("") }

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
                        text = geoPoint.latitude.format(6),
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
                        text = geoPoint.longitude.format(6),
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

                if (chosenImageBitmap == null) {
                    CustomActionButton(
                        title = "CHOOSE IMAGE",
                        backgroundColor = MaterialTheme.colorScheme.secondary,
                        modifier = Modifier.padding(top = 20.dp)
                    ) {
                        galleryLauncher.launch("image/*")
                    }
                } else {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 20.dp)
                    ) {
                        Button(
                            onClick = { galleryLauncher.launch("image/*") },
                            shape = RoundedCornerShape(30.dp),
                            modifier = Modifier
                                .fillMaxWidth(0.85f)
                                .height(58.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.secondary
                            )
                        ) {
                            Row(
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "Image: ${chosenImageBitmap.height}x${chosenImageBitmap.width} px.",
                                    fontSize = 20.sp,
                                    color = Color.White,
                                    fontWeight = FontWeight.Bold,
                                    overflow = TextOverflow.Ellipsis
                                )
                                Image(
                                    bitmap = chosenImageBitmap,
                                    contentDescription = null,
                                    modifier = Modifier
                                        .padding(start = 20.dp)
                                        .height(58.dp),
                                )
                            }
                        }


                        IconButton(onClick = { onImageRemove() }) {
                            Icon(
                                imageVector = Icons.Filled.Clear,
                                contentDescription = "Clear image button",
                                modifier = Modifier
                                    .size(48.dp),
                                tint = Color.Red
                            )
                        }
                    }

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