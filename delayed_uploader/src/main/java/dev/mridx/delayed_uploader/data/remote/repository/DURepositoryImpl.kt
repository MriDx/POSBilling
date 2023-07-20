package dev.mridx.delayed_uploader.data.remote.repository

import com.google.gson.Gson
import com.google.gson.JsonElement
import dev.mridx.common.common_data.data.remote.model.FileUploadResponseModel
import dev.mridx.common.common_data.data.remote.model.NetworkResource
import dev.mridx.common.common_data.data.remote.model.ResponseModel
import dev.mridx.common.common_data.utils.toRequestBody
import dev.mridx.common.common_utils.utils.parseException
import dev.mridx.delayed_uploader.data.remote.web_service.DUApiHelper
import dev.mridx.delayed_uploader.domain.repository.DURepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MultipartBody
import org.json.JSONObject
import javax.inject.Inject

class DURepositoryImpl @Inject constructor(
    private val duApiHelper: DUApiHelper,
    private val gson: Gson
) : DURepository {


    override suspend fun uploadFile(
        url: String,
        headers: Map<String, String>,
        part: MultipartBody.Part
    ): NetworkResource<FileUploadResponseModel> {
        return withContext(Dispatchers.IO) {

            try {

                val response = duApiHelper.uploadFile(url = url, headers = headers, bodyPart = part)

                if (!response.isSuccessful) {
                    val errorResponse = parseError(response = response, gson = gson)
                    throw Exception(errorResponse.message)
                }

                NetworkResource.success(data = response.body()!!)
            } catch (e: Exception) {
                e.printStackTrace()
                NetworkResource.error(parseException(e))
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


}