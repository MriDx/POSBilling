package dev.mridx.delayed_uploader.domain.repository

import com.google.gson.JsonElement
import dev.mridx.common.common_data.data.remote.model.FileUploadResponseModel
import dev.mridx.common.common_data.data.remote.model.NetworkResource
import dev.mridx.common.common_data.data.remote.model.ResponseModel
import dev.mridx.common.common_data.domain.repository.BaseRepository
import okhttp3.MultipartBody
import org.json.JSONObject

interface DURepository : BaseRepository {


    suspend fun uploadFile(
        url: String,
        headers: Map<String, String>,
        part: MultipartBody.Part,
    ): NetworkResource<FileUploadResponseModel>


    suspend fun submission(
        url: String,
        headers: Map<String, String>,
        params: JSONObject,
    ):  NetworkResource<ResponseModel<JsonElement>>


}