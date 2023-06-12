package com.example.apptemplate.data.remote.web_service.auth

import com.example.apptemplate.data.remote.model.auth.AuthResponseModel
import com.example.apptemplate.data.remote.model.user.AuthUserModel
import com.google.gson.JsonElement
import dev.mridx.common.common_data.data.remote.model.ResponseModel
import dev.mridx.common.common_data.di.GsonInterface
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.HeaderMap
import retrofit2.http.POST

interface AuthApiService {


    @GsonInterface
    @POST("v1/login")
    suspend fun login(
        @Body requestBody: RequestBody
    ): Response<ResponseModel<AuthResponseModel>>


    @GsonInterface
    @GET("v1/me")
    suspend fun getUserDetails(): Response<ResponseModel<AuthUserModel>>

    /**
     * This function will fetch users details and custom headers can be injected in it
     */
    @GsonInterface
    @GET("v1/me")
    suspend fun getUserDetailsWithHeaders(@HeaderMap headers: Map<String, String>): Response<ResponseModel<AuthUserModel>>


    @GsonInterface
    @POST("v1/logout")
    suspend fun logout(): Response<ResponseModel<JsonElement>>


}