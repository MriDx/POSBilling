package dev.mridx.delayed_uploader.data.remote.web_service

import com.google.gson.JsonElement
import dev.mridx.common.common_data.data.remote.model.FileUploadResponseModel
import dev.mridx.common.common_data.data.remote.model.ResponseModel
import dev.mridx.common.common_data.di.GsonInterface
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.HeaderMap
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Url

interface DUApiService {

    @GsonInterface
    @Multipart
    @POST
    suspend fun uploadFile(
        @Url url: String,
        @Part bodyPart: MultipartBody.Part,
        @HeaderMap headers: Map<String, String>,
    ): Response<FileUploadResponseModel>

    @GsonInterface
    @Multipart
    @POST
    suspend fun uploadFileDirect(
        @Url url: String,
        @Part bodyPart: MultipartBody.Part,
    ): Response<FileUploadResponseModel>

    @GsonInterface
    @Multipart
    @POST
    suspend fun uploadFileNew(
        @Url url: String,
        @Header(value = "Authorization") authorization: String,
        @Header(value = "Accept") accept: String,
        @Part bodyPart: MultipartBody.Part,
    ): Response<FileUploadResponseModel>


    @GsonInterface
    @POST
    suspend fun submission(
        @Url url: String,
        @Body params: RequestBody,
        @HeaderMap headers: Map<String, String>,
    ): Response<ResponseModel<JsonElement>>


    @GsonInterface
    @POST
    suspend fun submissionDirect(
        @Url url: String,
        @Body params: RequestBody,
    ): Response<ResponseModel<JsonElement>>


}