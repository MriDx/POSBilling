package dev.mridx.common.common_data.domain.repository

import com.google.gson.Gson
import dev.mridx.common.common_data.data.remote.model.ErrorResponse
import retrofit2.Response

interface BaseRepository {

    suspend fun <T> parseError(response: Response<T>, gson: Gson): ErrorResponse {
        return gson.fromJson(response.errorBody()?.charStream(), ErrorResponse::class.java)
    }

}