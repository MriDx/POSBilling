package dev.mridx.common.common_data.data.remote.model

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize


@Keep
@Parcelize
data class DateResponseModel(
    val human: String? = null,
    val date: String? = null,
    val formatted: String? = null,
) : Parcelable
