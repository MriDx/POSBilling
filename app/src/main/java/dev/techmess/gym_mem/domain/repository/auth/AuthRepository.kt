package dev.techmess.gym_mem.domain.repository.auth

import dev.techmess.gym_mem.data.remote.model.auth.AuthResponseModel
import com.google.gson.JsonElement
import dev.mridx.common.common_data.data.remote.model.NetworkResource
import dev.mridx.common.common_data.data.remote.model.ResponseModel
import dev.mridx.common.common_data.domain.repository.BaseRepository
import org.json.JSONObject

interface AuthRepository : BaseRepository {


    suspend fun login(params: JSONObject): NetworkResource<ResponseModel<AuthResponseModel>>


    suspend fun logout(): NetworkResource<ResponseModel<JsonElement>>


}