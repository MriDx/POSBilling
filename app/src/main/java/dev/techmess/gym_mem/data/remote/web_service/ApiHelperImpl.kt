package dev.techmess.gym_mem.data.remote.web_service

import dev.mridx.common.common_data.data.remote.model.FileUploadResponseModel
import okhttp3.MultipartBody
import retrofit2.Response
import javax.inject.Inject

class ApiHelperImpl @Inject constructor(
    private val apiService: ApiService
) : ApiHelper {



    override suspend fun uploadFile(body: MultipartBody.Part): Response<FileUploadResponseModel> =
        apiService.uploadFile(body)



}