package dev.techmess.gym_mem.presentation.user.fragment.profile.state

import dev.techmess.gym_mem.data.local.model.user.UserModel

sealed class ProfileFragmentState {

    data class UserFetched(val userModel: UserModel) : ProfileFragmentState()

}
