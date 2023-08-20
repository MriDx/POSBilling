package dev.techmess.gym_mem.data.local.model.action

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
    var isBlank: Boolean = false,
) : Parcelable {


    companion object {
        fun blankModel() = ActionModel("", -1, "", "", true)
    }

}