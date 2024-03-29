package com.example.exploremarks.domain

import com.example.exploremarks.data.model.MarkUIModel
import com.example.exploremarks.data.repository.MainRepository
import com.example.exploremarks.network.util.ApiResult

class GetMarksUseCase(
    private val repository: MainRepository
) {
    suspend operator fun invoke(): ApiResult<List<MarkUIModel>> {
        return repository.getMarks()
    }
}