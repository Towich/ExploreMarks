package com.example.exploremarks.ui.screen.map

import android.content.Context
import android.util.Log
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.exploremarks.R
import com.example.exploremarks.data.MarkUIModel
import com.example.exploremarks.ui.screen.map.components.MarkInfoButtonSheet
import com.example.exploremarks.ui.viewmodel.MapViewModel
import com.yandex.mapkit.Animation
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.map.CameraPosition
import com.yandex.mapkit.map.IconStyle
import com.yandex.mapkit.map.MapObjectCollection
import com.yandex.mapkit.map.MapObjectTapListener
import com.yandex.mapkit.mapview.MapView
import com.yandex.runtime.image.ImageProvider

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MapScreen(
    context: Context,
    viewModel: MapViewModel = hiltViewModel()
) {
    val listOfMarks by viewModel.listOfMarks.collectAsState(initial = null)
    var mapMarks by remember { mutableStateOf<MapObjectCollection?>(null) }
    var marksTapListeners = remember { mutableListOf<MapObjectTapListener>() }

    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    var showBottomSheet by remember { mutableStateOf(false) }

    var chosenMark: MarkUIModel? by remember { mutableStateOf(null) }

    val imageProvider = ImageProvider.fromResource(context, R.drawable.pin_blue_small)

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
            }
        },
        update = { mapView ->
            if (listOfMarks != null) {
                for (mark in listOfMarks!!) {
                    val point = Point(mark.latitude, mark.longitude)

                    val listener = MapObjectTapListener { _, _ ->
                        mapView.mapWindow.map.move(
                            CameraPosition(
                                Point(point.latitude, point.longitude), mapView.mapWindow.map.cameraPosition.zoom, 0f, 0f
                            ),
                            Animation(Animation.Type.SMOOTH, 1f),
                            {}
                        )
                        chosenMark = mark
                        showBottomSheet = true
                        true
                    }
                    marksTapListeners.add(listener)

                    mapMarks!!.addPlacemark().apply {
                        geometry = point
                        setIcon(imageProvider, IconStyle().setScale(0.5f))
                        addTapListener(listener)
                    }
                }
            }
        }
    )

//    FloatingActionButton(
//        onClick = {
//            clicked++
//            Log.i("MapScreen", "FAB!! clicked=$clicked")
//            mapMarks!!.addPlacemark().apply {
//                geometry = Point(55.751111, 37.610592)
//                setIcon(
//                    ImageProvider.fromResource(context, R.drawable.pin_blue_small),
//                    IconStyle().setScale(0.5f)
//                )
//            }
//        })
//    {}

    if (showBottomSheet) {
        MarkInfoButtonSheet(
            sheetState = sheetState,
            mark = chosenMark!!,
            onDismissRequest = {
                showBottomSheet = false
                chosenMark = null
            }
        )
    }
}