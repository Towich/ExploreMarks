package com.example.exploremarks.network.serializable

import com.example.exploremarks.data.model.UserUIModel
import com.example.exploremarks.network.serializer.UUIDSerializer
import kotlinx.serialization.Serializable
import java.util.UUID

@Serializable
data class UserSerializable(
    val id: @Serializable(with = UUIDSerializer::class) UUID,
    val username: String
){
    fun convertToUIModel() = UserUIModel(id, username)
}
