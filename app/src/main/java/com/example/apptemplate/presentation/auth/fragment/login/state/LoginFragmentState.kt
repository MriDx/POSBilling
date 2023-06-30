package com.example.apptemplate.presentation.auth.fragment.login.state

import com.example.apptemplate.data.remote.model.auth.AuthResponseModel
import dev.mridx.common.common_data.data.remote.model.NetworkResource
import dev.mridx.common.common_data.data.remote.model.ResponseModel

sealed class LoginFragmentState {

    data class LoginResponse(
        val response: NetworkResource<ResponseModel<AuthResponseModel>>,
        val message: String? = null
    ) : LoginFragmentState()
}