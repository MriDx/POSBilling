package dev.mridx.delayed_uploader.domain.repository

import com.google.gson.JsonElement
import dev.mridx.common.common_data.data.remote.model.FileUploadResponseModel
import dev.mridx.common.common_data.data.remote.model.NetworkResource
import dev.mridx.common.common_data.data.remote.model.ResponseModel
import dev.mridx.common.common_data.domain.repository.BaseRepository
import org.json.JSONObject

interface DURepository : BaseRepository {


    suspend fun uploadFile(
        url: String,
        headers: Map<String, String>,
        filePath: String,
        fileParam: String,
    ): NetworkResource<FileUploadResponseModel>

    suspend fun uploadFileDirect(
        url: String,
        filePath: String,
        fileParam: String,
    ): NetworkResource<FileUploadResponseModel>


    suspend fun submission(
        url: String,
        headers: Map<String, String>,
        params: JSONObject,
    ): NetworkResource<ResponseModel<JsonElement>>


    suspend fun submissionDirect(
        url: String,
        params: JSONObject,
    ): NetworkResource<ResponseModel<JsonElement>>


}