package dev.techmess.gym_mem.domain.use_case.user

import dev.mridx.common.common_data.data.remote.model.NetworkResource
import dev.mridx.common.common_data.data.remote.model.ResponseModel
import dev.techmess.gym_mem.data.local.model.user.UserModel
import dev.techmess.gym_mem.data.remote.model.user.AuthUserModel
import dev.techmess.gym_mem.domain.repository.user.UserRepository
import javax.inject.Inject

class UserUseCase @Inject constructor(private val userRepository: UserRepository) {


    suspend fun getAuthUserDetails(headers: Map<String, String>): NetworkResource<ResponseModel<AuthUserModel>> {
        return userRepository.getUserDetails(headers)
    }

    suspend fun storeLocalUser(authUserModel: AuthUserModel) {
        userRepository.storeUser(authUserModel)
    }

    suspend fun getLocalUser(): UserModel {
        return userRepository.fetchLocalUser()
    }

    suspend fun clearUserData() {
        userRepository.clearUserData()
    }

}