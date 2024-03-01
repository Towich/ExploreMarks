package com.example.exploremarks.data.repository

import android.app.Application
import com.example.exploremarks.data.DebugObject
import com.example.exploremarks.data.MarkUIModel
import com.example.exploremarks.network.ApiService

class MainRepository(
    private val appContext: Application,
    private val apiService: ApiService
) : IMainRepository {

    override suspend fun getMarks(): List<MarkUIModel>? {
        return apiService.getMarks() ?: DebugObject.listOfMarks
    }

}