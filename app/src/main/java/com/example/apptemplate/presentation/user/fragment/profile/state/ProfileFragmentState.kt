package com.example.apptemplate.presentation.user.fragment.profile.state

import com.example.apptemplate.data.local.model.user.UserModel

sealed class ProfileFragmentState {

    data class UserFetched(val userModel: UserModel) : ProfileFragmentState()

}
