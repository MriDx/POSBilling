package dev.techmess.gym_mem.data.remote.model.auth

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize


@Keep
@Parcelize
data class AuthResponseModel(
    val token_type: String,
    val access_token: String,
) : Parcelable
