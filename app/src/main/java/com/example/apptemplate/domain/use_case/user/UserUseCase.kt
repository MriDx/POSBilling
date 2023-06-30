package com.example.apptemplate.domain.use_case.user

import com.example.apptemplate.data.local.model.user.UserModel
import com.example.apptemplate.data.remote.model.user.AuthUserModel
import com.example.apptemplate.domain.repository.user.UserRepository
import dev.mridx.common.common_data.data.remote.model.NetworkResource
import dev.mridx.common.common_data.data.remote.model.ResponseModel
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