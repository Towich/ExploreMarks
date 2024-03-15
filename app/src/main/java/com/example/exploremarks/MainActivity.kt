package com.example.exploremarks

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import com.example.exploremarks.data.model.SessionMode
import com.example.exploremarks.data.source.CacheSession
import com.example.exploremarks.navigation.Navigation
import com.example.exploremarks.ui.theme.ExploreMarksTheme
import com.yandex.mapkit.MapKitFactory
import dagger.hilt.android.AndroidEntryPoint
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
        enableEdgeToEdge()

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


