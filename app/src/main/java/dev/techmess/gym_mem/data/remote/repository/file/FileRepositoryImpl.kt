package dev.techmess.gym_mem.data.remote.repository.file

import dev.techmess.gym_mem.data.remote.web_service.ApiHelper
import dev.techmess.gym_mem.di.qualifier.ApiHelperQualifier
import dev.techmess.gym_mem.domain.repository.file.FileRepository
import com.google.gson.Gson
import dev.mridx.common.common_data.data.remote.model.ErrorResponse
import dev.mridx.common.common_data.data.remote.model.FileUploadResponseModel
import dev.mridx.common.common_data.data.remote.model.NetworkResource
import dev.mridx.common.common_utils.utils.parseException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import java.io.FileNotFoundException
import javax.inject.Inject

class FileRepositoryImpl @Inject constructor(
    @ApiHelperQualifier
    private val apiHelper: ApiHelper,
    private val gson: Gson,
) : FileRepository {


    override suspend fun uploadFile(filePath: String): NetworkResource<FileUploadResponseModel> {
        return withContext(Dispatchers.IO) {

            try {
                val file = File(filePath)
                if (!file.exists()) throw FileNotFoundException("file not found")
                val requestFile = file.asRequestBody("application/octet-stream".toMediaType())
                val body = MultipartBody.Part.createFormData("file", file.name, requestFile)

                val response = apiHelper.uploadFile(body = body)

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

}