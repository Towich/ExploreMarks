package com.example.exploremarks.domain

import com.example.exploremarks.data.MarkUIModel
import com.example.exploremarks.data.repository.IMainRepository

class GetMarksUseCase(
    private val repository: IMainRepository
) {
    suspend operator fun invoke(): List<MarkUIModel>? {
        return repository.getMarks()
    }
}