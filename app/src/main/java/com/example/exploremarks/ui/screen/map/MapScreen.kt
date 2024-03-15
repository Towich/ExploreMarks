package com.example.exploremarks.ui.screen.map

import android.content.Context
import android.net.Uri
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
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.graphics.drawable.toDrawable
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.exploremarks.R
import com.example.exploremarks.data.model.SessionMode
import com.example.exploremarks.data.model.MarkUIModel
import com.example.exploremarks.data.util.Converter
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
import com.yandex.mapkit.map.PlacemarkMapObject
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

    val deleteMarkUiState by viewModel.deleteMarkUiState.collectAsState()
    var indexToRemove by remember { mutableStateOf<Int?>(null) }

    var newMark by remember { mutableStateOf<MarkUIModel?>(null) }
    val newMarkUiState by viewModel.newMarkUiState.collectAsState()

    var isMarksInitialized by remember { mutableStateOf(false) }

    var mapViewCollection by remember { mutableStateOf<MapObjectCollection?>(null) }
    var listOfPlacemarks = remember { mutableListOf<PlacemarkMapObject>() }
    val listOfMarksTapListeners = remember { mutableListOf<MapObjectTapListener>() }

    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    var showMarkInfoBottomSheet by remember { mutableStateOf(false) }

    var showNewMarkBottomSheet by remember { mutableStateOf(false) }
    var newGeoMark by remember { mutableStateOf<PlacemarkMapObject?>(null) }

    var chosenPointNewMark by remember { mutableStateOf(Point()) }
    var chosenImageBitmap by remember { mutableStateOf<ImageBitmap?>(null) }

    var chosenMark: MarkUIModel? by remember { mutableStateOf(null) }

    val imageProviderPinBlue = ImageProvider.fromResource(context, R.drawable.pin_blue_small)
    val imageProviderPinYellow = ImageProvider.fromResource(context, R.drawable.pin_yellow_small)

    when (newMarkUiState) {
        is MarkUiState.Success<*> -> {
            newMark = (newMarkUiState as MarkUiState.Success<*>).data as MarkUIModel
            viewModel.changeNewMarkUIState(MarkUiState.Initial)
        }

        else -> {}
    }

    when (deleteMarkUiState){
        is MarkUiState.Success<*> -> {
            indexToRemove = (deleteMarkUiState as MarkUiState.Success<*>).data as Int
            mapViewCollection?.remove(listOfPlacemarks[indexToRemove!!])
            listOfMarksTapListeners.removeAt(indexToRemove!!)
            listOfPlacemarks.removeAt(indexToRemove!!)
            indexToRemove = null
            showMarkInfoBottomSheet = false
            viewModel.changeDeleteMarkUIState(MarkUiState.Initial)
        }
        else -> {}
    }

//    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    val inputListener by remember {
        mutableStateOf(object : InputListener {
            override fun onMapTap(map: Map, point: Point) {

            }

            override fun onMapLongTap(map: Map, point: Point) {
                chosenPointNewMark = point
                showNewMarkBottomSheet = true

                map.move(
                    CameraPosition(
                        Point(point.latitude - 0.007f, point.longitude),
                        15f,
                        0f,
                        0f
                    ),
                    Animation(Animation.Type.SMOOTH, 2f)
                ) {}
                newGeoMark = mapViewCollection!!.addPlacemark().apply {
                    geometry = point
                    setIcon(
                        imageProviderPinYellow,
                        IconStyle().setScale(0.5f)
                    )
//                    addTapListener(listener)
                }
            }
        })
    }

    Scaffold {
        val darkTheme = isSystemInDarkTheme()

        AndroidView(
            factory = { context ->
                MapView(context).apply {
                    this.onStart()
                    mapViewCollection = this.mapWindow.map.mapObjects.addCollection()
                    this.mapWindow.map.move(
                        CameraPosition(
                            Point(55.751225, 37.629540),
                            /* zoom = */ 5.0f,
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
                                    Point(point.latitude - 0.0085f, point.longitude),
                                    15f,
                                    0f,
                                    0f
                                ),
                                Animation(Animation.Type.SMOOTH, 1f)
                            ) {}
                            chosenMark = mark
                            showMarkInfoBottomSheet = true
                            true
                        }
                        listOfMarksTapListeners.add(listener)

                        val placemarkMapObject = mapViewCollection!!.addPlacemark().apply {
                            geometry = point
                            setIcon(
                                if (sessionMode == SessionMode.AUTHORIZED && viewModel.userData.userId == mark.user?.id) imageProviderPinYellow else imageProviderPinBlue,
                                IconStyle().setScale(0.5f)
                            )
                            addTapListener(listener)
                        }

                        listOfPlacemarks.add(placemarkMapObject)
                    }

                    isMarksInitialized = true
                }
                if (newMark != null) {
                    val _newMark = newMark!!
                    newMark = null
                    val listener = MapObjectTapListener { _, _ ->
                        mapView.mapWindow.map.move(
                            CameraPosition(
                                Point(
                                    _newMark.latitude - 0.0085f,
                                    _newMark.longitude
                                ),
                                15f,
                                0f,
                                0f
                            ),
                            Animation(Animation.Type.SMOOTH, 1f)
                        ) {}
                        chosenMark = _newMark
                        showMarkInfoBottomSheet = true
                        true
                    }

                    listOfMarksTapListeners.add(listener)

                    val placemarkMapObject = mapViewCollection!!.addPlacemark().apply {
                        geometry = chosenPointNewMark
                        setIcon(
                            imageProviderPinYellow,
                            IconStyle().setScale(0.5f)
                        )
                        addTapListener(listener)
                    }

                    listOfPlacemarks.add(placemarkMapObject)

                    showNewMarkBottomSheet = false
                    chosenPointNewMark = Point()
                    newGeoMark = null

                    viewModel.changeNewMarkUIState(MarkUiState.Initial)
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
                    viewModel.performDeleteMark(markId)
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
                chosenImageBitmap = chosenImageBitmap,
                geoPoint = chosenPointNewMark,
                onCreateMark = { markUiModel ->
                    newGeoMark?.let { it1 -> mapViewCollection?.remove(it1) }

                    viewModel.performCreateMark(newMarkUIModel = markUiModel, imageMark = chosenImageBitmap)
                },
                onImageChosen = { uri: Uri? ->
                    if(uri != null){
                        chosenImageBitmap = viewModel.performGetImageBitmapByUri(uri = uri)
                        chosenImageBitmap
                    }
                    else{
                        null
                    }
                },
                onImageRemove = {
                    chosenImageBitmap = null
                },
                onDismissRequest = {
                    newGeoMark?.let { it1 -> mapViewCollection?.remove(it1) }
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