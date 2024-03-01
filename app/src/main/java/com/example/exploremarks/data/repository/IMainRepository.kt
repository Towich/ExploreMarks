package com.example.exploremarks.data.repository

import android.util.Log
import com.example.exploremarks.data.MarkUIModel

interface IMainRepository {
    suspend fun getMarks(): List<MarkUIModel>?
}