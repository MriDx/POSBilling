package com.example.apptemplate.presentation.auth.fragment.login_fragment.event

import org.json.JSONObject

sealed class LoginFragmentEvent {

    data class LoginUser(
        val login: String,
        val password: String,
        val deviceName: String
    ) : LoginFragmentEvent() {

        fun toJson(): JSONObject {
            return JSONObject().apply {
                put("login", login)
                put("email", login)
                put("password", password)
                put("device_name", deviceName)
            }
        }
    }

}