package com.example.exploremarks.data.model

import kotlinx.serialization.Serializable
import java.util.UUID


data class UserUIModel(
    val id: UUID,
    val username: String
)
