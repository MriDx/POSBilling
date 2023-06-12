package com.example.apptemplate.domain.use_case.user

import com.example.apptemplate.data.local.model.user.UserModel
import com.example.apptemplate.domain.repository.user.UserRepository
import javax.inject.Inject

class UseUseCase @Inject constructor(private val userRepository: UserRepository) {


    suspend fun getLocalUser(): UserModel {
        return userRepository.fetchLocalUser()
    }

}