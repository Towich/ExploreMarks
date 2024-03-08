package com.example.exploremarks.domain

import com.example.exploremarks.data.model.MarkUIModel
import com.example.exploremarks.data.model.UserData
import com.example.exploremarks.data.repository.MainRepository
import com.example.exploremarks.network.util.ApiResult
import java.util.UUID

class CreateMarkUseCase(
    private val repository: MainRepository
) {
    suspend operator fun invoke(newMark: MarkUIModel): ApiResult<MarkUIModel> {
        return repository.createMark(newMark)
    }
}