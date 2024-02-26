package com.example.exploremarks.ui.map

import android.content.Context
import android.util.Log
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.FloatingActionButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import com.example.exploremarks.R
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.map.CameraPosition
import com.yandex.mapkit.map.IconStyle
import com.yandex.mapkit.map.MapObjectCollection
import com.yandex.mapkit.mapview.MapView
import com.yandex.runtime.image.ImageProvider

@Composable
fun MapScreen(context: Context) {
    var clicked by remember { mutableIntStateOf(0) }
    var mapMarks by remember { mutableStateOf<MapObjectCollection?>(null) }

    AndroidView(
        factory = { context ->
            MapView(context).apply {
                mapMarks = this.mapWindow.map.mapObjects
                mapMarks!!.addPlacemark().apply {
                    geometry = Point(55.751225, 37.629540)
                    setIcon(ImageProvider.fromResource(context, R.drawable.pin_blue_small), IconStyle().setScale(0.5f))
                }
            }
        },
        update = { mapView ->
            mapView.onStart()
            mapView.mapWindow.map.move(
                CameraPosition(
                    Point(55.751225, 37.629540),
                    /* zoom = */ 10.0f,
                    /* azimuth = */ 0f,
                    /* tilt = */ 0f
                )
            )
        }
    )

    FloatingActionButton(
        onClick = {
            clicked++
            Log.i("MapScreen", "FAB!! clicked=$clicked")
        })
    {

    }
}