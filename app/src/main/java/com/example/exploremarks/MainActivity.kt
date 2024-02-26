package com.example.exploremarks

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.exploremarks.ui.map.MapScreen
import com.example.exploremarks.ui.theme.ExploreMarksTheme
import com.yandex.mapkit.MapKitFactory
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android

class MainActivity : ComponentActivity() {

    private fun setApiKey(savedInstanceState: Bundle?) {
        val haveApiKey = savedInstanceState?.getBoolean("haveApiKey") ?: false
        if (!haveApiKey) {
            MapKitFactory.setApiKey(BuildConfig.MAPKIT_API_KEY)
        }
    }

    override fun onStart() {
        super.onStart()
        MapKitFactory.getInstance().onStart()
    }

    override fun onStop() {
        MapKitFactory.getInstance().onStop()
        super.onStop()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putBoolean("haveMapApiKey", true)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // MapKit initialize
        setApiKey(savedInstanceState)
        MapKitFactory.initialize(this)

        setContent {
            ExploreMarksTheme {
                MapScreen(applicationContext)
            }
        }
    }
}


