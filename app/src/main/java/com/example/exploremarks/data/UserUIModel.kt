package com.example.exploremarks.data

import kotlinx.serialization.Serializable
import java.util.UUID


data class UserUIModel(
    val id: UUID,
    val username: String
)
