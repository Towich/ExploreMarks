package com.example.exploremarks.network

import com.example.exploremarks.data.model.MarkUIModel
import com.example.exploremarks.network.serializable.LoginRequestSerializable
import com.example.exploremarks.network.serializable.LoginResponseSerializable
import com.example.exploremarks.network.serializable.MarkSerializable
import com.example.exploremarks.network.serializable.RegisterRequestSerializable
import com.example.exploremarks.network.serializable.RegisterResponseSerializable
import com.example.exploremarks.network.util.ApiResult
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.forms.submitForm
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import io.ktor.http.parameters
import java.io.IOException

class ApiServiceImpl(private val client: HttpClient) : ApiService {

    override suspend fun register(userData: RegisterRequestSerializable): ApiResult<RegisterResponseSerializable> {
        val registerUrl = ApiRoutes.BASE_URL + ApiRoutes.REGISTER

        try{
            val response = client.post(registerUrl) {
                setBody(userData)
            }

            return when(response.status.value){
                in 200..299 -> {
                    ApiResult.Success(response.body())
                }

                400 -> {
                    ApiResult.Error("Error 400: User already exist!")
                }

                422 -> {
                    ApiResult.Error("Error 422: Invalid input!")
                }

                else -> {
                    ApiResult.Error("Code ${response.status.value}: ${response.status.description}")
                }
            }

        } catch (e: IOException) {
            return ApiResult.Error("No connection!")
        } catch (e: Exception) {
            return ApiResult.Error("${e.message}")
        }
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
                in 200..299 -> {
                    ApiResult.Success(response.body())
                }

                400, 422 -> {
                    ApiResult.Error("Error 400: Invalid username or password!")
                }

                else -> {
                    ApiResult.Error("Code ${response.status.value}: ${response.status.description}")
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