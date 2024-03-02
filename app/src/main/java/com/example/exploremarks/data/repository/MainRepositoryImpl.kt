package com.example.exploremarks.data.repository

import android.app.Application
import com.example.exploremarks.data.DebugObject
import com.example.exploremarks.data.MarkUIModel
import com.example.exploremarks.network.ApiService

class MainRepositoryImpl(
    private val appContext: Application,
    private val apiService: ApiService
) : MainRepository {

    override suspend fun getMarks(): List<MarkUIModel>? {
        return apiService.getMarks() ?: DebugObject.listOfMarks
    }

}