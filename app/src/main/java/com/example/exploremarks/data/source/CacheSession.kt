package com.example.exploremarks.data.source

import com.example.exploremarks.data.model.SessionMode
import com.example.exploremarks.data.model.UserData
import java.util.UUID

class CacheSession(
    private val sharedPref: SharedPref
) {
    var sessionMode: SessionMode = SessionMode.GUEST

    val userData = UserData(
        userId = UUID.fromString(sharedPref.getPreferencesString(SharedPref.USER_ID) ?: "00000000-0000-0000-0000-000000000000"),
        username = sharedPref.getPreferencesString(SharedPref.USERNAME),
        accessToken = sharedPref.getPreferencesString(SharedPref.ACCESS_TOKEN),
        tokenType = sharedPref.getPreferencesString(SharedPref.TOKEN_TYPE)
    )
}