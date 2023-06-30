package com.example.apptemplate.data.local.model.user

import android.os.Parcelable
import androidx.annotation.Keep
import com.example.apptemplate.data.constants.DESIGNATION
import com.example.apptemplate.data.constants.JOINED
import com.example.apptemplate.data.constants.PHOTO
import com.example.apptemplate.data.constants.ROLE
import com.example.apptemplate.data.constants.USER_EMAIL
import com.example.apptemplate.data.constants.USER_FULL_NAME
import com.example.apptemplate.data.constants.USER_PHONE
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
