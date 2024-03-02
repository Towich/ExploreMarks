package com.example.exploremarks.network.serializable

import com.example.exploremarks.data.MarkUIModel
import com.example.exploremarks.network.serializer.UUIDSerializer
import kotlinx.serialization.Serializable
import java.util.UUID

@Serializable
data class MarkSerializable(
    val id: @Serializable(with = UUIDSerializer::class) UUID,
    val latitude: Double,
    val longitude: Double,
    val description: String,
    val image: String?,
    val likes: Int,
    val is_liked: Boolean,
    val user: UserSerializable?
) {
    fun convertToUIModel() = MarkUIModel(
        id,
        latitude,
        longitude,
        description,
        image,
        likes,
        is_liked,
        user?.convertToUIModel()
    )
}
