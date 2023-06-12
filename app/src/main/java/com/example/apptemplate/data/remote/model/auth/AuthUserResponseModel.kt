package com.example.apptemplate.data.remote.model.auth

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize


@Keep
@Parcelize
data class AuthUserResponseModel(
    val name: String,
    val email: String? = null,
    val phone: String? = null,
) : Parcelable
