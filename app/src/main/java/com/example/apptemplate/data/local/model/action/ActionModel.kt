package com.example.apptemplate.data.local.model.action

import android.os.Parcelable
import androidx.annotation.DrawableRes
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
data class ActionModel(
    val heading: String,
    @DrawableRes val icon: Int,
    val id: String,
    var supportingText: String? = null,
) : Parcelable