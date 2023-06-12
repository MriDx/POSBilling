package com.example.apptemplate.data.remote.model.user

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
data class AuthUserModel(
    val name: String,
) : Parcelable
