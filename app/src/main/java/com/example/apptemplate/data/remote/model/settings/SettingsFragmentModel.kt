package com.example.apptemplate.data.remote.model.settings

import android.os.Parcelable
import androidx.annotation.Keep
import com.example.apptemplate.data.local.model.action.ActionModel
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
data class SettingsFragmentModel(
    var actions: List<ActionModel> = emptyList(),
) : Parcelable