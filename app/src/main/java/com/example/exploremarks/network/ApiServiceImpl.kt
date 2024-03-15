package com.example.exploremarks.network

import android.graphics.Bitmap
import androidx.compose.ui.graphics.asAndroidBitmap
import com.example.exploremarks.data.model.MarkUIModel
import com.example.exploremarks.network.serializable.LoginRequestSerializable
import com.example.exploremarks.network.serializable.LoginResponseSerializable
import com.example.exploremarks.network.serializable.MarkSerializable
import com.example.exploremarks.network.serializable.RegisterRequestSerializable
import com.example.exploremarks.network.serializable.RegisterResponseSerializable
import com.example.exploremarks.network.util.ApiResult
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.delete
import io.ktor.client.request.forms.formData
import io.ktor.client.request.forms.submitForm
import io.ktor.client.request.forms.submitFormWithBinaryData
import io.ktor.client.request.get
import io.ktor.client.request.headers
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import io.ktor.http.Headers
import io.ktor.http.HttpHeaders
import io.ktor.http.parameters
import io.ktor.utils.io.core.buildPacket
import io.ktor.utils.io.core.writeFully
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.util.UUID

class ApiServiceImpl(
    private val client: HttpClient
) : ApiService {

    override suspend fun register(userData: RegisterRequestSerializable): ApiResult<RegisterResponseSerializable> {
        val url = ApiRoutes.BASE_URL + ApiRoutes.REGISTER

        try {
            val response = client.post(url) {
                setBody(userData)
            }

            return when (response.status.value) {
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
        val url = ApiRoutes.BASE_URL + ApiRoutes.LOGIN

        try {
            val response = client.submitForm(
                url = url,
                formParameters = parameters {
                    append("grant_type", userData.grant_type)
                    append("username", userData.username)
                    append("password", userData.password)
                    append("scope", userData.scope)
                    append("client_id", userData.client_id)
                    append("client_secret", userData.client_secret)
                }
            )

            return when (response.status.value) {
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

    override suspend fun getMarks(
        accessToken: String?,
        tokenType: String?
    ): ApiResult<List<MarkUIModel>> {
        val url = ApiRoutes.BASE_URL + ApiRoutes.TAGS

        try {
            val response = client.get {
                url(url)
                headers {
                    if (accessToken != null && tokenType != null) {
                        append(HttpHeaders.Authorization, "$tokenType $accessToken")
                    }
                }
            }

            return when (response.status.value) {
                in 200..299 -> {
                    ApiResult.Success(
                        response.body<List<MarkSerializable>>().map { it.convertToUIModel() })
                }

                422 -> {
                    ApiResult.Error("Error 422: Validation Error!")
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

    override suspend fun likeMark(
        accessToken: String?,
        tokenType: String?,
        markId: UUID
    ): ApiResult<MarkUIModel> {
        val url = ApiRoutes.BASE_URL + ApiRoutes.TAGS + "$markId" + ApiRoutes.LIKES

        try {
            val response = client.post {
                url(url)
                headers {
                    if (accessToken != null && tokenType != null) {
                        append(HttpHeaders.Authorization, "$tokenType $accessToken")
                    }
                }
            }

            return when (response.status.value) {
                201 -> {
                    ApiResult.Success(response.body<MarkSerializable>().convertToUIModel())
                }

                422 -> {
                    ApiResult.Error("Error 422: Validation Error!")
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

    override suspend fun dislikeMark(
        accessToken: String?,
        tokenType: String?,
        markId: UUID
    ): ApiResult<Boolean> {
        val url = ApiRoutes.BASE_URL + ApiRoutes.TAGS + "$markId" + ApiRoutes.LIKES

        try {
            val response = client.delete {
                url(url)
                headers {
                    if (accessToken != null && tokenType != null) {
                        append(HttpHeaders.Authorization, "$tokenType $accessToken")
                    }
                }
            }

            return when (response.status.value) {
                204 -> {
                    ApiResult.Success(true)
                }

                422 -> {
                    ApiResult.Error("Error 422: Validation Error!")
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

    override suspend fun createMark(
        newMark: MarkUIModel,
        imageByteArray: ByteArray?,
        accessToken: String?,
        tokenType: String?
    ): ApiResult<MarkUIModel> {
        val url = ApiRoutes.BASE_URL + ApiRoutes.TAGS

        try {
            val response = client.submitFormWithBinaryData(
                url = url,
                formData = formData {
                    append("latitude", newMark.latitude.toString())
                    append("longitude", newMark.longitude.toString())
                    append("description", newMark.description)

                    if (imageByteArray != null) {
                        appendInput(key = "image", headers = Headers.build {
                            append(
                                HttpHeaders.ContentDisposition,
                                "filename=image.jpg"
                            )
                        }){
                            buildPacket { writeFully(imageByteArray) }
                        }
                    }
                }
            ){
                headers {
                    if (accessToken != null && tokenType != null) {
                        append(HttpHeaders.Authorization, "$tokenType $accessToken")
                    }
                }
            }

            return when (response.status.value) {
                201 -> {
                    ApiResult.Success(response.body<MarkSerializable>().convertToUIModel())
                }

                422 -> {
                    ApiResult.Error("Error 422: Validation Error!")
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

    override suspend fun deleteMark(
        markId: UUID,
        accessToken: String?,
        tokenType: String?
    ): ApiResult<Boolean> {
        val url = ApiRoutes.BASE_URL + ApiRoutes.TAGS + "$markId"

        try {
            val response = client.delete {
                url(url)
                headers {
                    if (accessToken != null && tokenType != null) {
                        append(HttpHeaders.Authorization, "$tokenType $accessToken")
                    }
                }
            }


            return when (response.status.value) {
                204 -> {
                    ApiResult.Success(true)
                }

                422 -> {
                    ApiResult.Error("Error 422: Validation Error!")
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
}