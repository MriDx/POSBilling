package dev.mridx.delayed_uploader.data.remote.web_service

import com.google.gson.JsonElement
import dev.mridx.common.common_data.data.remote.model.FileUploadResponseModel
import dev.mridx.common.common_data.data.remote.model.ResponseModel
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import javax.inject.Inject

class DUApiHelperImpl @Inject constructor(
    private val duApiService: DUApiService,
) : DUApiHelper {

    override suspend fun uploadFile(
        url: String, headers: Map<String, String>, bodyPart: MultipartBody.Part
    ): Response<FileUploadResponseModel> =
        duApiService.uploadFile(url = url, bodyPart = bodyPart, headers = headers)

    override suspend fun uploadFileDirect(
        url: String, bodyPart: MultipartBody.Part
    ): Response<FileUploadResponseModel> =
        duApiService.uploadFileDirect(url = url, bodyPart = bodyPart)

    override suspend fun uploadFileNew(
        url: String, authorization: String, accept: String, bodyPart: MultipartBody.Part
    ): Response<FileUploadResponseModel> = duApiService.uploadFileNew(
        url = url, authorization = authorization, accept = accept, bodyPart = bodyPart
    )


    override suspend fun submission(
        url: String, headers: Map<String, String>, params: RequestBody
    ): Response<ResponseModel<JsonElement>> =
        duApiService.submission(url = url, headers = headers, params = params)


    override suspend fun submissionDirect(
        url: String,
        params: RequestBody
    ): Response<ResponseModel<JsonElement>> =
        duApiService.submissionDirect(url = url, params = params)

}