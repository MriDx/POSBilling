package dev.techmess.gym_mem.domain.repository.user

import dev.mridx.common.common_data.data.remote.model.NetworkResource
import dev.mridx.common.common_data.data.remote.model.ResponseModel
import dev.mridx.common.common_data.domain.repository.BaseRepository
import dev.techmess.gym_mem.data.local.model.user.UserModel
import dev.techmess.gym_mem.data.remote.model.user.AuthUserModel

interface UserRepository : BaseRepository {

    suspend fun getUserDetails(headers: Map<String, String>): NetworkResource<ResponseModel<AuthUserModel>>

    suspend fun storeUser(authUserModel: AuthUserModel)

    suspend fun fetchLocalUser(): UserModel

    suspend fun clearUserData()


}