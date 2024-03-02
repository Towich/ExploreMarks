package com.example.exploremarks.network.serializable

import kotlinx.serialization.Serializable

@Serializable
data class RegisterRequestSerializable(
    val username: String,
    val password: String
)