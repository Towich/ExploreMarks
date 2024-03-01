package com.example.exploremarks.data

import java.util.UUID

object DebugObject {
    val user1 = UserUIModel(
        id = UUID.randomUUID(),
        username = "Towich"
    )

    val user2 = UserUIModel(
        id = UUID.randomUUID(),
        username = "Oleg Tinkoff"
    )

    val listOfMarks = listOf(
        MarkUIModel(
            id = UUID.randomUUID(),
            latitude = 55.751111,
            longitude = 37.610592,
            description = "first mark",
            image = "3124fged",
            likes = 5,
            isLiked = true,
            user = user1
        ),
        MarkUIModel(
            id = UUID.randomUUID(),
            latitude = 55.751225,
            longitude = 37.629540,
            description = "first mark",
            image = "3124fged",
            likes = 1,
            isLiked = false,
            user = user2
        )
    )
}