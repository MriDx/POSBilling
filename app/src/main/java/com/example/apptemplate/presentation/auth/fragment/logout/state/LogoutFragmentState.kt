package com.example.apptemplate.presentation.auth.fragment.logout.state

import com.google.gson.JsonElement
import dev.mridx.common.common_data.data.remote.model.NetworkResource
import dev.mridx.common.common_data.data.remote.model.ResponseModel

sealed class LogoutFragmentState {


    data class LogoutResponse(
        val response: NetworkResource<ResponseModel<JsonElement>>,
        val message: String? = null,
    ) : LogoutFragmentState()


}