package com.example.exploremarks.network

import com.example.exploremarks.data.MarkUIModel
import com.example.exploremarks.network.serializable.MarkSerializable
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.url

class ApiServiceImpl(private val client: HttpClient) : ApiService {
    override suspend fun getMarks(): List<MarkUIModel>? {
        return try {
            val marks: List<MarkSerializable> = client.get {
                url(ApiRoutes.BASE_URL + ApiRoutes.TAGS)
            }.body()

            marks.map { it.convertToUIModel() }
        } catch (ex: Exception) {
            null
        }
    }
}