package dev.mridx.delayed_uploader.data.remote.repository

import com.google.gson.Gson
import com.google.gson.JsonElement
import dev.mridx.common.common_data.data.remote.model.ErrorResponse
import dev.mridx.common.common_data.data.remote.model.FileUploadResponseModel
import dev.mridx.common.common_data.data.remote.model.NetworkResource
import dev.mridx.common.common_data.data.remote.model.ResponseModel
import dev.mridx.common.common_data.utils.toRequestBody
import dev.mridx.common.common_utils.utils.parseException
import dev.mridx.delayed_uploader.data.remote.web_service.DUApiHelper
import dev.mridx.delayed_uploader.domain.repository.DURepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import org.json.JSONObject
import java.io.File
import java.io.FileNotFoundException
import javax.inject.Inject

class DURepositoryImpl @Inject constructor(
    private val duApiHelper: DUApiHelper,
    private val gson: Gson
) : DURepository {


    override suspend fun uploadFile(
        url: String,
        headers: Map<String, String>,
        filePath: String,
        fileParam: String
    ): NetworkResource<FileUploadResponseModel> {
        return withContext(Dispatchers.IO) {

            try {
                val file = File(filePath)
                if (!file.exists()) throw FileNotFoundException("file not found")
                val requestFile = file.asRequestBody("application/octet-stream".toMediaType())
                val body = MultipartBody.Part.createFormData("file", file.name, requestFile)

//                val bodyParams = MultipartBody.Builder().setType(MultipartBody.FORM)
//                    .addFormDataPart(name = fileParam, filename = file.name, body = requestFile).build()

                val response = duApiHelper.uploadFile(url = url, headers = headers, bodyPart = body)


                if (!response.isSuccessful) {
                    val error =
                        gson.fromJson(response.errorBody()?.charStream(), ErrorResponse::class.java)
                    throw Exception(
                        error.message ?: "Something went wrong ! Please try again after some time."
                    )
                }

                NetworkResource.success(data = response.body()!!)

            } catch (e: Exception) {
                e.printStackTrace()
                NetworkResource.error(message = parseException(e))
            }

            /*try {

                val response = duApiHelper.uploadFile(url = url, headers = headers, bodyPart = part)

                if (!response.isSuccessful) {
                    val errorResponse = parseError(response = response, gson = gson)
                    throw Exception(errorResponse.message)
                }

                NetworkResource.success(data = response.body()!!)
            } catch (e: Exception) {
                e.printStackTrace()
                NetworkResource.error(parseException(e))
            }*/

        }
    }


    override suspend fun uploadFileDirect(
        url: String,
        filePath: String,
        fileParam: String
    ): NetworkResource<FileUploadResponseModel> {
        return withContext(Dispatchers.IO) {
            try {
                val file = File(filePath)
                if (!file.exists()) throw FileNotFoundException("file not found")
                val requestFile = file.asRequestBody("application/octet-stream".toMediaType())
                val body = MultipartBody.Part.createFormData("file", file.name, requestFile)

                val response = duApiHelper.uploadFileDirect(url = url, bodyPart = body)


                if (!response.isSuccessful) {
                    val error =
                        gson.fromJson(response.errorBody()?.charStream(), ErrorResponse::class.java)
                    throw Exception(
                        error.message ?: "Something went wrong ! Please try again after some time."
                    )
                }

                NetworkResource.success(data = response.body()!!)

            } catch (e: Exception) {
                e.printStackTrace()
                NetworkResource.error(message = parseException(e))
            }
        }
    }


    override suspend fun submission(
        url: String,
        headers: Map<String, String>,
        params: JSONObject
    ): NetworkResource<ResponseModel<JsonElement>> {
        return withContext(Dispatchers.IO) {

            try {


                val requestBody = params.toRequestBody()

                val response = duApiHelper.submission(
                    url = url,
                    headers = headers,
                    params = requestBody
                )

                if (!response.isSuccessful) {
                    val errorResponse = parseError(response = response, gson = gson)
                    return@withContext NetworkResource.error(
                        data = ResponseModel.withError<JsonElement>(
                            errorResponse = errorResponse
                        ),
                        message = errorResponse.message
                    )
                }

                NetworkResource.success(data = response.body()!!)

            } catch (e: Exception) {
                e.printStackTrace()
                NetworkResource.error(
                    message = parseException(e)
                )
            }

        }
    }

    override suspend fun submissionDirect(
        url: String,
        params: JSONObject
    ): NetworkResource<ResponseModel<JsonElement>> {
        return withContext(Dispatchers.IO) {

            try {

                val requestBody = params.toRequestBody()

                val response = duApiHelper.submissionDirect(
                    url = url,
                    params = requestBody
                )

                if (!response.isSuccessful) {
                    val errorResponse = parseError(response = response, gson = gson)
                    return@withContext NetworkResource.error(
                        data = ResponseModel.withError<JsonElement>(
                            errorResponse = errorResponse
                        ),
                        message = errorResponse.message
                    )
                }

                NetworkResource.success(data = response.body()!!)

            } catch (e: Exception) {
                e.printStackTrace()
                NetworkResource.error(
                    message = parseException(e)
                )
            }

        }
    }



}