package com.example.apptemplate.data.remote.model.home

import android.os.Parcelable
import androidx.annotation.Keep
import com.example.apptemplate.data.local.model.action.ActionModel
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
data class HomeFragmentModel(
    val name: String = "",
    var actions: List<ActionModel> = emptyList(),
) : Parcelable