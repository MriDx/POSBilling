package dev.techmess.gym_mem.data.remote.model.user

import android.os.Parcelable
import androidx.annotation.Keep
import dev.mridx.common.common_data.data.remote.model.DateResponseModel
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
data class AuthUserModel(
    val name: String,
    val email: String? = null,
    val role: String? = null,
    val phone: String? = null,
    val photo: String? = null,
    val designation: String? = null,
    val created: DateResponseModel? = null,
) : Parcelable
