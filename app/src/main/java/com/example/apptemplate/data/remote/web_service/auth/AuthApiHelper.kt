package com.example.apptemplate.data.remote.web_service.auth

import com.example.apptemplate.data.remote.model.auth.AuthResponseModel
import com.example.apptemplate.data.remote.model.user.AuthUserModel
import com.google.gson.JsonElement
import dev.mridx.common.common_data.data.remote.model.ResponseModel
import okhttp3.RequestBody
import retrofit2.Response

interface AuthApiHelper {


    suspend fun login(
        requestBody: RequestBody
    ): Response<ResponseModel<AuthResponseModel>>


    suspend fun getUserDetails(): Response<ResponseModel<AuthUserModel>>


    suspend fun getUserDetailsWithHeaders(headers: Map<String, String>): Response<ResponseModel<AuthUserModel>>


    suspend fun logout(): Response<ResponseModel<JsonElement>>

}