package com.example.exploremarks.data.repository

import com.example.exploremarks.data.MarkUIModel

interface MainRepository {
    suspend fun getMarks(): List<MarkUIModel>?
}