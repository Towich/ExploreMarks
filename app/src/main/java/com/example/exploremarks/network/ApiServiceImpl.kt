package com.example.exploremarks.network

import com.example.exploremarks.data.MarkUIModel
import com.example.exploremarks.network.serializable.LoginRequestSerializable
import com.example.exploremarks.network.serializable.LoginResponseSerializable
import com.example.exploremarks.network.serializable.MarkSerializable
import com.example.exploremarks.network.serializable.RegisterRequestSerializable
import com.example.exploremarks.network.serializable.RegisterResponseSerializable
import com.example.exploremarks.network.util.ApiResult
import com.example.exploremarks.network.util.ErrorCode
import com.example.exploremarks.ui.screen.login.LoginScreenUiState
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.ResponseException
import io.ktor.client.request.forms.submitForm
import io.ktor.client.request.get
import io.ktor.client.request.url
import io.ktor.http.HttpStatusCode
import io.ktor.http.parameters
import kotlinx.serialization.SerializationException
import java.io.IOException

class ApiServiceImpl(private val client: HttpClient) : ApiService {

    override suspend fun register(userData: RegisterRequestSerializable): ApiResult<RegisterResponseSerializable>? {
//        return try {
//            val marks: List<MarkSerializable> = client.post {
//                url(ApiRoutes.BASE_URL + ApiRoutes.TAGS)
//            }.body()
//
//            marks.map { it.convertToUIModel() }
//        } catch (ex: Exception) {
//            null
//        }
        return null
    }

    override suspend fun login(userData: LoginRequestSerializable): ApiResult<LoginResponseSerializable> {
        val loginUrl = ApiRoutes.BASE_URL + ApiRoutes.LOGIN

        try{
            val response = client.submitForm(
                url = loginUrl,
                formParameters = parameters {
                    append("grant_type", userData.grant_type)
                    append("username", userData.username)
                    append("password", userData.password)
                    append("scope", userData.scope)
                    append("client_id", userData.client_id)
                    append("client_secret", userData.client_secret)
                }
            )

            return when(response.status.value){
                200 -> {
                    ApiResult.Success(response.body())
                }

                400, 422 -> {
                    ApiResult.Error("Code 400: Invalid username or password!")
                }

                else -> {
                    ApiResult.Error("Unknown error!")
                }
            }

        } catch (e: IOException) {
            return ApiResult.Error("No connection!")
        } catch (e: Exception) {
            return ApiResult.Error("${e.message}")
        }
    }

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