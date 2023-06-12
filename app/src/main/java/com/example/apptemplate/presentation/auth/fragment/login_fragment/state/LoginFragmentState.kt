package com.example.apptemplate.presentation.auth.fragment.login_fragment.state

import com.google.gson.JsonElement
import dev.mridx.common.common_data.data.remote.model.NetworkResource
import dev.mridx.common.common_data.data.remote.model.ResponseModel

sealed class LoginFragmentState {

    data class LoginResponse(
        val response: NetworkResource<ResponseModel<JsonElement>>? = null,
        val message: String? = null
    ) : LoginFragmentState()
}