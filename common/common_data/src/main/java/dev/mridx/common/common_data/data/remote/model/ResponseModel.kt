package dev.mridx.common.common_data.data.remote.model

import android.os.Parcelable
import androidx.annotation.Keep
import com.google.gson.JsonObject
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

@Keep
@Parcelize
data class ResponseModel<T>(
    val status: Int = 0,
    val message: String? = null,
    val data: @RawValue T? = null,
    val errorsResponse: ErrorResponse? = null
) : Parcelable {

    companion object {

        fun <T> withError(errorResponse: ErrorResponse?): ResponseModel<T> {
            return ResponseModel(errorsResponse = errorResponse)
        }

    }

    fun hasErrors(): Boolean = errorsResponse != null

    fun hasInlineErrors(): Boolean =
        if (!hasErrors()) false else errorsResponse!!.errors != null && errorsResponse.errors!!.isJsonObject

    fun getInlineErrors(): JsonObject = errorsResponse!!.errors!!.asJsonObject

}

