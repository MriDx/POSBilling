package com.example.apptemplate.data.remote.web_service.auth

import com.example.apptemplate.data.remote.model.auth.AuthResponseModel
import com.example.apptemplate.data.remote.model.user.AuthUserModel
import com.google.gson.JsonElement
import dev.mridx.common.common_data.data.remote.model.ResponseModel
import okhttp3.RequestBody
import retrofit2.Response
import javax.inject.Inject

class AuthApiHelperImpl @Inject constructor(private val authApiService: AuthApiService) :
    AuthApiHelper {


    override suspend fun login(requestBody: RequestBody): Response<ResponseModel<AuthResponseModel>> =
        authApiService.login(requestBody)

    override suspend fun getUserDetails(): Response<ResponseModel<AuthUserModel>> =
        authApiService.getUserDetails()

    override suspend fun getUserDetailsWithHeaders(headers: Map<String, String>): Response<ResponseModel<AuthUserModel>> =
        authApiService.getUserDetailsWithHeaders(headers)

    override suspend fun logout(): Response<ResponseModel<JsonElement>> = authApiService.logout()


}