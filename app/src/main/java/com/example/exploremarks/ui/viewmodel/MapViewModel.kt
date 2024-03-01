package com.example.exploremarks.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.example.exploremarks.data.DebugObject
import com.example.exploremarks.data.MarkUIModel
import com.example.exploremarks.data.repository.IMainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

@HiltViewModel
class MapViewModel @Inject constructor(
    private val repository: IMainRepository
): ViewModel() {
    val listOfMarks: Flow<List<MarkUIModel>?> = flow {
        delay(2000L)
        emit(DebugObject.listOfMarks)
    }
}