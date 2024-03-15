package com.example.exploremarks.data.source

import android.app.Application
import android.content.Context

class SharedPref(
    appContext: Application
) {
    private val sharedPref = appContext.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)

    fun savePreferencesString(key: String, value: String){
        with(sharedPref.edit()){
            putString(key, value)
            apply()
        }
    }

    fun getPreferencesString(key: String): String? {
        return sharedPref.getString(key, null)
    }

    fun removePreference(key: String){
        with(sharedPref.edit()){
            remove(key)
            apply()
        }
    }

    companion object Keys {
        const val SHARED_PREF_NAME = "APP_PREFERENCES"
        const val ACCESS_TOKEN = "ACCESS_TOKEN"
        const val TOKEN_TYPE = "TOKEN_TYPE"
        const val USER_ID = "USER_ID"
        const val USERNAME = "USERNAME"
    }
}