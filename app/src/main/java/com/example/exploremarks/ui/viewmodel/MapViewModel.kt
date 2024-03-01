package com.example.exploremarks.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.example.exploremarks.data.DebugObject
import com.example.exploremarks.data.MarkUIModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class MapViewModel(

): ViewModel() {
    val listOfMarks: Flow<List<MarkUIModel>?> = flow {
        delay(2000L)
        emit(DebugObject.listOfMarks)
    }
}