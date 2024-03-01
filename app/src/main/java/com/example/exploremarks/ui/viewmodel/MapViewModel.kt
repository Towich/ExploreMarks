package com.example.exploremarks.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.example.exploremarks.data.DebugObject
import com.example.exploremarks.data.MarkUIModel
import com.example.exploremarks.data.repository.IMainRepository
import com.example.exploremarks.domain.GetMarksUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

@HiltViewModel
class MapViewModel @Inject constructor(
    private val getMarks: GetMarksUseCase
): ViewModel() {
    val listOfMarks: Flow<List<MarkUIModel>?> = flow {
        val marks = getMarks()
        emit(marks)

        // emit(DebugObject.listOfMarks)
    }
}