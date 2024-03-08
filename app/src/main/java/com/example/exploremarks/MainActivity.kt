package com.example.exploremarks

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.exploremarks.data.SessionMode
import com.example.exploremarks.data.model.CacheSession
import com.example.exploremarks.navigation.Navigation
import com.example.exploremarks.ui.screen.map.MapScreen
import com.example.exploremarks.ui.theme.ExploreMarksTheme
import com.yandex.mapkit.MapKitFactory
import dagger.hilt.android.AndroidEntryPoint
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val isMapInitializedKey = "isMapInitializedKey"

    @Inject
    lateinit var cacheSession: CacheSession

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
        outState.putBoolean(isMapInitializedKey, true)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // MapKit initialize
        setApiKey(savedInstanceState)
        MapKitFactory.initialize(this)

        val isAuthorized = cacheSession.userData.accessToken != null
        if(isAuthorized){
            cacheSession.sessionMode = SessionMode.AUTHORIZED
        }

        setContent {
            val navController = rememberNavController()

            ExploreMarksTheme {
                Navigation(
                    context = applicationContext,
                    navController = navController,
                    isAuthorized = isAuthorized,
                    cacheSession = cacheSession
                )
            }
        }
    }

    private fun setApiKey(savedInstanceState: Bundle?) {
        val haveApiKey = savedInstanceState?.getBoolean(isMapInitializedKey) ?: false
        if (!haveApiKey) {
            MapKitFactory.setApiKey(BuildConfig.MAPKIT_API_KEY)
        }
    }
}


