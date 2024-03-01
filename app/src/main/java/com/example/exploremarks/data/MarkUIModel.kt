package com.example.exploremarks.data

import java.util.UUID

data class MarkUIModel(
    val id: UUID,
    val latitude: Double,
    val longitude: Double,
    val description: String,
    val image: String?,
    val likes: Int,
    val isLiked: Boolean,
    val user: UserUIModel?
)
