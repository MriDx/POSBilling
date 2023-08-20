package dev.techmess.gym_mem.domain.use_case.auth

import dev.techmess.gym_mem.data.remote.model.auth.AuthResponseModel
import dev.techmess.gym_mem.domain.repository.auth.AuthRepository
import com.google.gson.JsonElement
import dev.mridx.common.common_data.data.remote.model.NetworkResource
import dev.mridx.common.common_data.data.remote.model.ResponseModel
import org.json.JSONObject
import javax.inject.Inject

class AuthUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {

    suspend fun emailPassLogin(params: JSONObject): NetworkResource<ResponseModel<AuthResponseModel>> {
        return authRepository.login(params)
    }


    suspend fun logout(): NetworkResource<ResponseModel<JsonElement>> {
        return authRepository.logout()
    }


}