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
            description = "second mark",
            image = "3124fged",
            likes = 1,
            isLiked = false,
            user = user2
        ),
        MarkUIModel(
            id = UUID.randomUUID(),
            latitude = 55.758467,
            longitude = 37.615762,
            description = "Что-то во дворе гос думы",
            image = null,
            likes = 0,
            isLiked = false,
            user = null
        )
    )
}