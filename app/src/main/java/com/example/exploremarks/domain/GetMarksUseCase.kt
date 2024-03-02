package com.example.exploremarks.domain

import com.example.exploremarks.data.model.MarkUIModel
import com.example.exploremarks.data.repository.MainRepository

class GetMarksUseCase(
    private val repository: MainRepository
) {
    suspend operator fun invoke(): List<MarkUIModel>? {
        return repository.getMarks()
    }
}