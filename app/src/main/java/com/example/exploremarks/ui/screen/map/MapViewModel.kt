package com.example.exploremarks.ui.screen.map

import androidx.lifecycle.ViewModel
import com.example.exploremarks.data.model.MarkUIModel
import com.example.exploremarks.domain.GetMarksUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
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