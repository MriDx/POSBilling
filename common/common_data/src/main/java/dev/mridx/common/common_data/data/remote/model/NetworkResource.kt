package dev.mridx.common.common_data.data.remote.model

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue


@Keep
@Parcelize
data class NetworkResource<T>(
    var status: ResponseStatus,
    var data: @RawValue T? = null,
    var message: String? = null
) : Parcelable {


    companion object {

        fun <T> success(data: T?) = NetworkResource(status = ResponseStatus.SUCCESS, data = data)

        fun <T> error(message: String?, data: T? = null) =
            NetworkResource(status = ResponseStatus.FAILED, data = data, message = message)

    }

    fun isSuccess() = status == ResponseStatus.SUCCESS

    fun isFailed() = status == ResponseStatus.FAILED


}
