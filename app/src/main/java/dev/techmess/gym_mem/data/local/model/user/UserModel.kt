package dev.techmess.gym_mem.data.local.model.user

import android.os.Parcelable
import androidx.annotation.Keep
import dev.techmess.gym_mem.data.constants.DESIGNATION
import dev.techmess.gym_mem.data.constants.GYM_NAME
import dev.techmess.gym_mem.data.constants.JOINED
import dev.techmess.gym_mem.data.constants.PHOTO
import dev.techmess.gym_mem.data.constants.ROLE
import dev.techmess.gym_mem.data.constants.USER_EMAIL
import dev.techmess.gym_mem.data.constants.USER_FULL_NAME
import dev.techmess.gym_mem.data.constants.USER_PHONE
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
data class UserModel(
    val name: String = "",
    val email: String = "",
    val designation: String = "",
    val phone: String = "",
    val photo: String = "",
    val role: String = "",
    val joined: String = "",
    val gymName: String = "",
) : Parcelable {


    companion object {

        fun fromMap(details: Map<String, *>): UserModel {
            return UserModel(
                name = details[USER_FULL_NAME].toString(),
                email = details[USER_EMAIL].toString(),
                designation = details[DESIGNATION].toString(),
                phone = details[USER_PHONE].toString(),
                photo = details[PHOTO].toString(),
                role = details[ROLE].toString(),
                joined = details[JOINED].toString(),
                gymName = details[GYM_NAME].toString(),
            )
        }

    }


    fun getMapForProfile(): Map<String, String?> {
        return mapOf(
            "Email" to email,
            "Phone" to phone,
            "Joined on" to joined
        )
    }

}
