package dev.techmess.gym_mem.data.local.model.web_view

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize


@Keep
@Parcelize
data class WebViewModel(
    val url: String,
) : Parcelable
