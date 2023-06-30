package com.example.apptemplate.domain.repository.user

import com.example.apptemplate.data.local.model.user.UserModel
import com.example.apptemplate.data.remote.model.user.AuthUserModel
import dev.mridx.common.common_data.data.remote.model.NetworkResource
import dev.mridx.common.common_data.data.remote.model.ResponseModel
import dev.mridx.common.common_data.domain.repository.BaseRepository

interface UserRepository : BaseRepository {

    suspend fun getUserDetails(headers: Map<String, String>): NetworkResource<ResponseModel<AuthUserModel>>

    suspend fun storeUser(authUserModel: AuthUserModel)

    suspend fun fetchLocalUser(): UserModel

    suspend fun clearUserData()


}