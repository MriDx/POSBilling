package dev.techmess.gym_mem.data.local.model.media

import android.os.Parcelable
import androidx.annotation.Keep
import dev.mridx.common.common_data.data.remote.model.ImageModel
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
data class ImageViewModel(
    val imageModel: ImageModel?,
    val caption: String? = null
) : Parcelable
