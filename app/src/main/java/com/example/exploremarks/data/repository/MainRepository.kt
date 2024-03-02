package com.example.exploremarks.data.repository

import com.example.exploremarks.data.model.MarkUIModel

interface MainRepository {
    suspend fun getMarks(): List<MarkUIModel>?
}