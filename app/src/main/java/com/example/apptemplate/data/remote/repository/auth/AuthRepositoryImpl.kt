package com.example.apptemplate.data.remote.repository.auth

import android.content.SharedPreferences
import androidx.core.content.edit
import com.example.apptemplate.data.remote.model.auth.AuthResponseModel
import com.example.apptemplate.data.remote.web_service.auth.AuthApiHelper
import com.example.apptemplate.domain.repository.auth.AuthRepository
import com.example.apptemplate.domain.use_case.user.UserUseCase
import com.google.gson.Gson
import com.google.gson.JsonElement
import dev.mridx.common.common_data.data.constants.ACCESS_TOKEN
import dev.mridx.common.common_data.data.remote.model.NetworkResource
import dev.mridx.common.common_data.data.remote.model.ResponseModel
import dev.mridx.common.common_data.di.qualifier.AppPreference
import dev.mridx.common.common_data.utils.toRequestBody
import dev.mridx.common.common_utils.utils.parseException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authApiHelper: AuthApiHelper,
    private val gson: Gson,
    private val userUseCase: UserUseCase,
    @AppPreference
    private val appSharedPreferences: SharedPreferences,
) : AuthRepository {


    override suspend fun login(params: JSONObject): NetworkResource<ResponseModel<AuthResponseModel>> {
        return withContext(Dispatchers.IO) {

            try {

                val response = authApiHelper.login(params.toRequestBody())


                if (!response.isSuccessful) {
                    val errorResponse = parseError(response = response, gson = gson)
                    return@withContext NetworkResource.error(
                        data = ResponseModel.withError<AuthResponseModel>(
                            errorResponse = errorResponse,
                        ), message = errorResponse.message
                    )
                }

                // TODO: get user details by calling user details function from userUseCase

                appSharedPreferences.edit {
                    putString(ACCESS_TOKEN, response.body()!!.data?.let {
                        "${it.token_type} ${it.access_token}"
                    })
                }

                val userDetailsResponse =
                    userUseCase.getAuthUserDetails(headers = mapOf("Authorization" to (response.body()!!.data?.let {
                        "${it.token_type} ${it.access_token}"
                    } ?: "")

                    ))

                if (userDetailsResponse.isFailed()) {
                    return@withContext NetworkResource.error(
                        data = ResponseModel.withError(errorResponse = userDetailsResponse.data?.errorsResponse),
                        message = ""
                    )
                }

                userUseCase.storeLocalUser(authUserModel = userDetailsResponse.data!!.data!!)

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
            } catch (e: Exception) {
                e.printStackTrace()
            }

            //clear user data
            userUseCase.clearUserData()

            NetworkResource.success(data = null)
        }
    }

}