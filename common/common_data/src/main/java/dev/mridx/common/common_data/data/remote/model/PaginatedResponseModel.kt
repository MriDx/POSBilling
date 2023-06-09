package dev.mridx.common.common_data.data.remote.model

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue



@Keep
@Parcelize
data class PaginatedResponseModel<Data>(
    val data: @RawValue Data,
    var links: LinksModel,
) : Parcelable


@Keep
@Parcelize
data class LinksModel(
    var first: String? = null,
    var last: String? = null,
    var prev: String? = null,
    var next: String? = null,
) : Parcelable
