package dev.techmess.gym_mem.data.remote.repository.user

import android.content.SharedPreferences
import androidx.core.content.edit
import com.google.gson.Gson
import dev.mridx.common.common_data.data.constants.LOGGED_IN
import dev.mridx.common.common_data.data.remote.model.NetworkResource
import dev.mridx.common.common_data.data.remote.model.ResponseModel
import dev.mridx.common.common_data.di.qualifier.AppPreference
import dev.mridx.common.common_utils.utils.parseException
import dev.techmess.gym_mem.data.constants.DESIGNATION
import dev.techmess.gym_mem.data.constants.PHOTO
import dev.techmess.gym_mem.data.constants.ROLE
import dev.techmess.gym_mem.data.constants.USER_EMAIL
import dev.techmess.gym_mem.data.constants.USER_FULL_NAME
import dev.techmess.gym_mem.data.constants.USER_PHONE
import dev.techmess.gym_mem.data.local.model.user.UserModel
import dev.techmess.gym_mem.data.remote.model.user.AuthUserModel
import dev.techmess.gym_mem.data.remote.web_service.auth.AuthApiHelper
import dev.techmess.gym_mem.domain.repository.user.UserRepository
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

                //val response = authApiHelper.getUserDetailsWithHeaders(headers = headers)
                val response = authApiHelper.getUserDetails()

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
                putString(USER_FULL_NAME, authUserModel.name)
                putString(USER_EMAIL, authUserModel.email)
                putString(USER_PHONE, authUserModel.phone)
                putString(ROLE, authUserModel.role)
                putString(PHOTO, authUserModel.photo)
                putString(DESIGNATION, authUserModel.designation)
                putBoolean(LOGGED_IN, true)
            }
        }
    }

    override suspend fun fetchLocalUser(): UserModel {
        return withContext(Dispatchers.IO) {
            UserModel.fromMap(appSharedPreferences.all)
        }
    }


    override suspend fun clearUserData() {
        withContext(Dispatchers.IO) {
            appSharedPreferences.edit {
                clear()
            }
        }
    }

}