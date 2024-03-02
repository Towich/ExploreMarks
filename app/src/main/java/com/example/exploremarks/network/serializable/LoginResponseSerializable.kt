package com.example.exploremarks.network.serializable

import kotlinx.serialization.Serializable

@Serializable
data class LoginResponseSerializable(
    val access_token: String,
    val token_type: String
)
