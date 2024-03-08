package com.example.exploremarks.domain

import com.example.exploremarks.data.model.UserData
import com.example.exploremarks.data.repository.MainRepository
import java.util.UUID

class ClearUserDataUseCase(
    private val repository: MainRepository
) {
    operator fun invoke() {
        return repository.clearUserData()
    }
}