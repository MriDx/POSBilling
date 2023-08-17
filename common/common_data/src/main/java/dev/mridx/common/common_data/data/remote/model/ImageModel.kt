package dev.mridx.common.common_data.data.remote.model

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
data class ImageModel(
    val image: String,
    val img_meta: ImageMeta,
) : Parcelable


@Keep
@Parcelize
data class ImageMeta(
    val img_thumb: String? = null,
    val img_large: String? = null,
) : Parcelable