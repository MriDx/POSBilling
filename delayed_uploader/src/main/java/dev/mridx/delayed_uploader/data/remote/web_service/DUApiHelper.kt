package dev.mridx.delayed_uploader.data.remote.web_service

import com.google.gson.JsonElement
import dev.mridx.common.common_data.data.remote.model.FileUploadResponseModel
import dev.mridx.common.common_data.data.remote.model.ResponseModel
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response

interface DUApiHelper {


    suspend fun uploadFile(
        url: String,
        headers: Map<String, String>,
        bodyPart: MultipartBody.Part,
    ): Response<FileUploadResponseModel>


    suspend fun submission(
        url: String,
        headers: Map<String, String>,
        params: RequestBody,
    ): Response<ResponseModel<JsonElement>>


}