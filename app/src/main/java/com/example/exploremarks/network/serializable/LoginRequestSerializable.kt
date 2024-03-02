package com.example.exploremarks.network.serializable

import kotlinx.serialization.Serializable

@Serializable
data class LoginRequestSerializable(
    val grant_type: String = "",
    val username: String,
    val password: String,
    val scope: String = "",
    val client_id: String = "",
    val client_secret: String = ""
)
