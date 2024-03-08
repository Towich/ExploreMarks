package com.example.exploremarks.data.model

import java.util.UUID

data class MarkUIModel(
    val id: UUID,
    val latitude: Double,
    val longitude: Double,
    val description: String,
    val image: String?,
    var likes: Int,
    var isLiked: Boolean,
    val user: UserUIModel?
)
