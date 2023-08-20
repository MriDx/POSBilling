package dev.techmess.gym_mem.data.remote.web_service

import dev.mridx.common.common_data.data.remote.model.FileUploadResponseModel
import okhttp3.MultipartBody
import retrofit2.Response

interface ApiHelper {


    suspend fun uploadFile(body: MultipartBody.Part): Response<FileUploadResponseModel>


}