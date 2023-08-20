package dev.techmess.gym_mem.data.remote.model.member

import android.os.Parcelable
import androidx.annotation.Keep
import dev.mridx.dynamic_form.data.model.DynamicFieldModel
import kotlinx.parcelize.Parcelize


@Keep
@Parcelize
data class AddMemberFragmentModel(
    val fields: List<DynamicFieldModel> = emptyList()
) : Parcelable
