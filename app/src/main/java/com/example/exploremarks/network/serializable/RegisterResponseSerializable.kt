package com.example.exploremarks.network.serializable

import com.example.exploremarks.network.serializer.UUIDSerializer
import kotlinx.serialization.Serializable
import java.util.UUID

@Serializable
data class RegisterResponseSerializable(
    val id: @Serializable(with = UUIDSerializer::class) UUID,
    val username: String
)
