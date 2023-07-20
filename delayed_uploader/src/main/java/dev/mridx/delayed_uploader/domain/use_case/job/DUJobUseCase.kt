package dev.mridx.delayed_uploader.domain.use_case.job

import com.google.gson.JsonElement
import dev.mridx.common.common_data.data.remote.model.NetworkResource
import dev.mridx.common.common_data.data.remote.model.ResponseModel
import dev.mridx.delayed_uploader.domain.repository.DURepository
import org.json.JSONObject
import javax.inject.Inject

class DUJobUseCase @Inject constructor(
    private val duRepository: DURepository,
) {

    suspend fun submitJob(
        url: String,
        headers: Map<String, String>,
        params: JSONObject,
    ): NetworkResource<ResponseModel<JsonElement>> {
        return duRepository.submission(url = url, headers = headers, params = params)
    }

}