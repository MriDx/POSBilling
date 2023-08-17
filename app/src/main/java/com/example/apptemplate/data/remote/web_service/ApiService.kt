package com.example.apptemplate.data.remote.web_service

import dev.mridx.common.common_data.data.remote.model.FileUploadResponseModel
import dev.mridx.common.common_data.di.GsonInterface
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface ApiService {


    @GsonInterface
    @Multipart
    @POST("v1/uploads")
    suspend fun uploadFile(@Part body: MultipartBody.Part): Response<FileUploadResponseModel>


}