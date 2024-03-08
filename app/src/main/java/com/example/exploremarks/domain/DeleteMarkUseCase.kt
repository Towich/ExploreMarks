package com.example.exploremarks.domain

import com.example.exploremarks.data.model.MarkUIModel
import com.example.exploremarks.data.model.UserData
import com.example.exploremarks.data.repository.MainRepository
import com.example.exploremarks.network.util.ApiResult
import java.util.UUID

class DeleteMarkUseCase(
    private val repository: MainRepository
) {
    suspend operator fun invoke(markId: UUID): ApiResult<Boolean> {
        return repository.deleteMark(markId = markId)
    }
}