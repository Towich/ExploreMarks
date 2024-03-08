package com.example.exploremarks.domain

import com.example.exploremarks.data.model.UserData
import com.example.exploremarks.data.repository.MainRepository
import java.util.UUID

class GetUserDataUseCase(
    private val repository: MainRepository
) {
    operator fun invoke(): UserData {
        return repository.getUserData()
    }
}