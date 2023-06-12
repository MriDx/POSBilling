package com.example.apptemplate.data.remote.web_service.auth

import com.google.gson.JsonElement
import dev.mridx.common.common_data.data.remote.model.ResponseModel
import okhttp3.RequestBody
import retrofit2.Response

interface AuthApiHelper {


    suspend fun login(
        requestBody: RequestBody
    ): Response<ResponseModel<JsonElement>>


    suspend fun logout(): Response<ResponseModel<JsonElement>>

}