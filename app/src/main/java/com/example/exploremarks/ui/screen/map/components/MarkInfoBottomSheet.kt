package com.example.exploremarks.ui.screen.map.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.SubcomposeAsyncImage
import com.example.exploremarks.R
import com.example.exploremarks.data.model.MarkUIModel
import com.example.exploremarks.data.model.SessionMode
import com.example.exploremarks.data.util.format
import com.example.exploremarks.network.ApiRoutes
import com.example.exploremarks.ui.screen.map.MarkUiState
import java.util.UUID

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun MarkInfoBottomSheet(
    sheetState: SheetState,
    markUiState: MarkUiState,
    mark: MarkUIModel,
    sessionMode: SessionMode,
    userId: UUID,
    onRemoveMark: (markId: UUID) -> Unit,
    onLikeMark: (markId: UUID) -> Unit,
    onDislikeMark: (markId: UUID) -> Unit,
    onDismissRequest: () -> Unit
) {
    ModalBottomSheet(
        onDismissRequest = {
            onDismissRequest()
        },
        sheetState = sheetState,
        containerColor = MaterialTheme.colorScheme.background,
        scrimColor = Color.Transparent
    ) {
        // Sheet content

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp),
            contentAlignment = Alignment.Center
        ) {
            SubcomposeAsyncImage(
                model = if(mark.image != null) ApiRoutes.BASE_URL + mark.image else null,
                contentDescription = "Image of mark",
                modifier = Modifier,
                contentScale = ContentScale.FillHeight,
                loading = {
                    CircularProgressIndicator(
                        strokeWidth = 10.dp,
                        modifier = Modifier
                            .size(150.dp),
                    )
                },
                error = {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(300.dp)
                            .border(width = 1.dp, color = MaterialTheme.colorScheme.primary),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "No photo!",
                            textAlign = TextAlign.Center
                        )
                    }
                }
            )
        }


//        Image(
//            painter = painterResource(id = R.drawable.zaradye),
//            contentDescription = null,
//            modifier = Modifier
//                .fillMaxWidth()
//                .height(300.dp),
//            contentScale = ContentScale.FillWidth
//        )

        Row(
            modifier = Modifier
                .padding(top = 20.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Column(
                modifier = Modifier,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Latitude",
                    color = MaterialTheme.colorScheme.secondary,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                )
                Text(
                    text = mark.latitude.format(6),
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Bold,

                    )
            }
            Column(
                modifier = Modifier,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Longitude",
                    color = MaterialTheme.colorScheme.secondary,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                )
                Text(
                    text = mark.longitude.format(6),
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        Row(
            modifier = Modifier
                .padding(top = 20.dp, start = 20.dp, end = 20.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Author",
                color = MaterialTheme.colorScheme.secondary,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
            )
            Text(
                text = mark.user?.username ?: "Unknown",
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
            )
        }

        Row(
            modifier = Modifier
                .padding(top = 20.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Description",
                color = MaterialTheme.colorScheme.secondary,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
            )
        }

        FlowRow(
            modifier = Modifier
                .padding(top = 20.dp, start = 20.dp, end = 20.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = mark.description,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Bold
            )
        }

//        Row(
//            modifier = Modifier
//                .padding(top = 30.dp)
//                .fillMaxWidth(),
//            horizontalArrangement = Arrangement.Center
//        ) {
//            Text(
//                text = "${mark.likes}",
//                fontSize = 24.sp
//            )
//            Icon(
//                painter = painterResource(id = R.drawable.heart_red_icon),
//                contentDescription = null,
//                tint = Color.Unspecified,
//                modifier = Modifier
//                    .padding(start = 5.dp)
//            )
//        }


        Row(
            modifier = Modifier
                .padding(top = 50.dp, start = 20.dp, end = 20.dp, bottom = 20.dp)
                .fillMaxWidth(),
            horizontalArrangement = if (userId == mark.user?.id && sessionMode == SessionMode.AUTHORIZED) Arrangement.SpaceBetween else Arrangement.End
        ) {

            if (userId == mark.user?.id && sessionMode == SessionMode.AUTHORIZED) {
                Box(
                    modifier = Modifier
                        .size(64.dp)
                        .clip(CircleShape)
                        .background(color = MaterialTheme.colorScheme.primary)
                        .clickable {
                            onRemoveMark(mark.id)
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.trash_can_icon),
                        contentDescription = null,
                        tint = Color.Unspecified,
                        modifier = Modifier
                            .size(32.dp)
                    )
                }
            }

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "${mark.likes}",
                    fontSize = 28.sp,
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Bold
                )
                Box(
                    modifier = Modifier
                        .padding(start = 10.dp)
                        .size(64.dp)
                        .clip(CircleShape)
                        .background(color = MaterialTheme.colorScheme.primary)
                        .clickable {
                            if (sessionMode == SessionMode.AUTHORIZED) {
                                if (!mark.isLiked) {
                                    onLikeMark(mark.id)
                                } else {
                                    onDislikeMark(mark.id)
                                }
                            }
                        },
                    contentAlignment = Alignment.Center
                ) {
                    if (markUiState is MarkUiState.Loading) {
                        CircularProgressIndicator()
                    } else {
                        Icon(
                            painter = if (mark.isLiked) painterResource(id = R.drawable.heart_filled_icon) else painterResource(
                                id = R.drawable.heart_empty_icon
                            ),
                            contentDescription = null,
                            tint = Color.Unspecified,
                            modifier = Modifier
                                .size(32.dp)
                        )
                    }
                }
            }
        }
    }
}