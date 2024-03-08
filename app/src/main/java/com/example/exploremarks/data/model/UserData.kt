package com.example.exploremarks.data.model

import java.util.UUID

data class UserData(
    var userId: UUID,
    var username: String?,
    var accessToken: String?,
    var tokenType: String?
)
