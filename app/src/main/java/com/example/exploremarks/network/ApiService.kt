package com.example.exploremarks.network

import com.example.exploremarks.data.MarkUIModel


interface ApiService {
    suspend fun getMarks(): List<MarkUIModel>?
}