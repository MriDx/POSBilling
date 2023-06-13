package com.example.apptemplate.data.remote.repository.user

import android.content.SharedPreferences
import androidx.core.content.edit
import com.example.apptemplate.data.local.model.user.UserModel
import com.example.apptemplate.data.remote.model.user.AuthUserModel
import com.example.apptemplate.data.remote.web_service.auth.AuthApiHelper
import com.example.apptemplate.domain.repository.user.UserRepository
import com.google.gson.Gson
import dev.mridx.common.common_data.data.remote.model.NetworkResource
import dev.mridx.common.common_data.data.remote.model.ResponseModel
import dev.mridx.common.common_data.di.qualifier.AppPreference
import dev.mridx.common.common_utils.utils.parseException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val authApiHelper: AuthApiHelper,
    @AppPreference
    private val appSharedPreferences: SharedPreferences,
    private val gson: Gson,
) : UserRepository {


    override suspend fun getUserDetails(headers: Map<String, String>): NetworkResource<ResponseModel<AuthUserModel>> {
        return withContext(Dispatchers.IO) {
            try {

                val response = authApiHelper.getUserDetailsWithHeaders(headers = headers)

                if (!response.isSuccessful) {
                    val errorResponse = parseError(response = response, gson = gson)

                    return@withContext NetworkResource.error(
                        message = errorResponse.message,
                        data = ResponseModel.withError<AuthUserModel>(
                            errorResponse = errorResponse
                        )
                    )
                }

                NetworkResource.success(data = response.body()!!)

            } catch (e: Exception) {
                e.printStackTrace()
                NetworkResource.error(message = parseException(e))
            }
        }
    }


    override suspend fun storeUser(authUserModel: AuthUserModel) {
        withContext(Dispatchers.IO) {
            appSharedPreferences.edit {
                // TODO: store user details here
            }
        }
    }

    override suspend fun fetchLocalUser(): UserModel {
        return withContext(Dispatchers.IO) {
            UserModel.fromMap(appSharedPreferences.all)
        }
    }

}