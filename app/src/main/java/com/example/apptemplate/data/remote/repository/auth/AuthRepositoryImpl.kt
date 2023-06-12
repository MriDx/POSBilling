package com.example.apptemplate.data.remote.repository.auth

import com.example.apptemplate.data.remote.web_service.auth.AuthApiHelper
import com.example.apptemplate.domain.repository.auth.AuthRepository
import com.google.gson.Gson
import com.google.gson.JsonElement
import dev.mridx.common.common_data.data.remote.model.NetworkResource
import dev.mridx.common.common_data.data.remote.model.ResponseModel
import dev.mridx.common.common_data.utils.toRequestBody
import dev.mridx.common.common_utils.utils.parseException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authApiHelper: AuthApiHelper,
    private val gson: Gson
) : AuthRepository {


    override suspend fun login(params: JSONObject): NetworkResource<ResponseModel<JsonElement>> {
        return withContext(Dispatchers.IO) {

            try {

                val response = authApiHelper.login(params.toRequestBody())



                if (!response.isSuccessful) {
                    val errorResponse = parseError(response = response, gson = gson)
                    return@withContext NetworkResource.error(
                        data = ResponseModel.withError<JsonElement>(
                            errorResponse = errorResponse,
                        ),
                        message = errorResponse.message
                    )
                }

                NetworkResource.success(data = response.body()!!)


            } catch (e: Exception) {
                e.printStackTrace()
                NetworkResource.error(message = parseException(e))
            }

        }
    }


    override suspend fun logout(): NetworkResource<ResponseModel<JsonElement>> {
        return withContext(Dispatchers.IO) {
            try {

                val response = authApiHelper.logout()

                //clear user data


            } catch (e: Exception) {
                e.printStackTrace()
            }
            NetworkResource.success(data = null)
        }
    }

}