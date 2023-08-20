package dev.techmess.gym_mem.data.remote.model.settings

import android.os.Parcelable
import androidx.annotation.Keep
import dev.techmess.gym_mem.data.local.model.action.ActionModel
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
data class SettingsFragmentModel(
    var actions: List<ActionModel> = emptyList(),
) : Parcelable