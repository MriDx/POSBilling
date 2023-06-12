package com.example.apptemplate.data.remote.web_service.auth

import com.google.gson.JsonElement
import dev.mridx.common.common_data.data.remote.model.ResponseModel
import dev.mridx.common.common_data.di.GsonInterface
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApiService {


    @GsonInterface
    @POST("v1/login")
    suspend fun login(
        @Body requestBody: RequestBody
    ): Response<ResponseModel<JsonElement>>


    @GsonInterface
    @POST("v1/logout")
    suspend fun logout() : Response<ResponseModel<JsonElement>>


}