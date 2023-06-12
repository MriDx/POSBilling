package com.example.apptemplate.data.remote.web_service.auth

import com.google.gson.JsonElement
import dev.mridx.common.common_data.data.remote.model.ResponseModel
import okhttp3.RequestBody
import retrofit2.Response
import javax.inject.Inject

class AuthApiHelperImpl @Inject constructor(private val authApiService: AuthApiService) :
    AuthApiHelper {


    override suspend fun login(requestBody: RequestBody): Response<ResponseModel<JsonElement>> =
        authApiService.login(requestBody)


    override suspend fun logout(): Response<ResponseModel<JsonElement>> = authApiService.logout()



}