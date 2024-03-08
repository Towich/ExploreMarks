package com.example.exploremarks.data.model

import java.util.UUID

data class MarkUIModel(
    val id: UUID,
    val latitude: Double,
    val longitude: Double,
    val description: String,
    val image: String? = null,
    var likes: Int = 0,
    var isLiked: Boolean = false,
    val user: UserUIModel? = null
)
