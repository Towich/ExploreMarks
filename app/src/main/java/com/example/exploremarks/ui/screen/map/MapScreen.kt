package com.example.exploremarks.ui.screen.map

import android.content.Context
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.exploremarks.R
import com.example.exploremarks.data.SessionMode
import com.example.exploremarks.data.model.MarkUIModel
import com.example.exploremarks.ui.screen.map.components.MarkInfoButtonSheet
import com.example.exploremarks.ui.screen.map.components.NewMarkButtonSheet
import com.yandex.mapkit.Animation
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.map.CameraPosition
import com.yandex.mapkit.map.IconStyle
import com.yandex.mapkit.map.InputListener
import com.yandex.mapkit.map.Map
import com.yandex.mapkit.map.MapObjectCollection
import com.yandex.mapkit.map.MapObjectTapListener
import com.yandex.mapkit.mapview.MapView
import com.yandex.runtime.image.ImageProvider

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MapScreen(
    context: Context,
    sessionMode: SessionMode,
    viewModel: MapViewModel = hiltViewModel()
) {
    val uiState by viewModel.screenUiState.collectAsState()
    val listOfMarks by viewModel.listOfMarks.collectAsState()
    val markUiState by viewModel.markUiState.collectAsState()

    var isMarksInitialized by remember { mutableStateOf(false) }

    var mapMarks by remember { mutableStateOf<MapObjectCollection?>(null) }
    val marksTapListeners = remember { mutableListOf<MapObjectTapListener>() }

    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    var showMarkInfoBottomSheet by remember { mutableStateOf(false) }
    var showNewMarkBottomSheet by remember { mutableStateOf(false) }

    var chosenMark: MarkUIModel? by remember { mutableStateOf(null) }

    val imageProviderPinBlue = ImageProvider.fromResource(context, R.drawable.pin_blue_small)
    val imageProviderPinYellow = ImageProvider.fromResource(context, R.drawable.pin_yellow_small)

//    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    val inputListener by remember {
        mutableStateOf(object : InputListener {
            override fun onMapTap(p0: Map, p1: Point) {

            }

            override fun onMapLongTap(p0: Map, p1: Point) {

            }

        })
    }

    Scaffold {
        val darkTheme = isSystemInDarkTheme()

        AndroidView(
            factory = { context ->
                MapView(context).apply {
                    this.onStart()
                    mapMarks = this.mapWindow.map.mapObjects.addCollection()
                    this.mapWindow.map.move(
                        CameraPosition(
                            Point(55.751225, 37.629540),
                            /* zoom = */ 10.0f,
                            /* azimuth = */ 0f,
                            /* tilt = */ 0f
                        )
                    )
                    this.mapWindow.map.addInputListener(inputListener)
                    Log.i("MAP_SCREEN", "session mode=${sessionMode.name}")

                    this.mapWindow.map.isNightModeEnabled = darkTheme
                }
            },
            update = { mapView ->
                if (listOfMarks != null && !isMarksInitialized) {
                    for (mark in listOfMarks!!) {
                        val point = Point(mark.latitude, mark.longitude)

                        val listener = MapObjectTapListener { _, _ ->
                            mapView.mapWindow.map.move(
                                CameraPosition(
                                    Point(point.latitude, point.longitude),
                                    mapView.mapWindow.map.cameraPosition.zoom,
                                    0f,
                                    0f
                                ),
                                Animation(Animation.Type.SMOOTH, 1f),
                                {}
                            )
                            chosenMark = mark
                            showMarkInfoBottomSheet = true
                            true
                        }
                        marksTapListeners.add(listener)

                        mapMarks!!.addPlacemark().apply {
                            geometry = point
                            setIcon(
                                if (sessionMode == SessionMode.AUTHORIZED && viewModel.userData.userId == mark.user?.id) imageProviderPinYellow else imageProviderPinBlue,
                                IconStyle().setScale(0.5f)
                            )
                            addTapListener(listener)
                        }
                    }

                    isMarksInitialized = true
                }
            },
            modifier = Modifier
                .padding(it)
        )

        if (showMarkInfoBottomSheet) {
            MarkInfoButtonSheet(
                sheetState = sheetState,
                markUiState = markUiState,
                mark = chosenMark!!,
                userId = viewModel.getUserId(),
                sessionMode = sessionMode,
                onRemoveMark = { markId ->

                },
                onLikeMark = { markId ->
                    viewModel.performLikeMark(markId = markId)
                },
                onDislikeMark = { markId ->
                    viewModel.performDislikeMark(markId = markId)
                },
                onDismissRequest = {
                    showMarkInfoBottomSheet = false
                    chosenMark = null
                }
            )
        }

        if (showNewMarkBottomSheet) {
            NewMarkButtonSheet(
                sheetState = sheetState,
                sessionMode = sessionMode,
                onCreateMark = { newMark ->

                },
                onDismissRequest = {
                    showNewMarkBottomSheet = false
                }
            )
        }

        if (uiState is MapScreenUiState.Loading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background.copy(alpha = 0.5f)),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(150.dp),
                    strokeWidth = 10.dp
                )
            }
        }
    }

}