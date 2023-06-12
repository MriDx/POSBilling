package com.example.apptemplate.data.remote.repository.user

import android.content.SharedPreferences
import com.example.apptemplate.data.local.model.user.UserModel
import com.example.apptemplate.domain.repository.user.UserRepository
import dev.mridx.common.common_data.di.qualifier.AppPreference
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    @AppPreference
    private val appSharedPreferences: SharedPreferences,
) : UserRepository {

    override suspend fun fetchLocalUser(): UserModel {
        return withContext(Dispatchers.IO) {
            UserModel.fromMap(appSharedPreferences.all)
        }
    }

}