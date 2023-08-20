package dev.techmess.gym_mem.presentation.app.fragment.home.state

import dev.techmess.gym_mem.data.local.model.user.UserModel
import dev.techmess.gym_mem.data.remote.model.home.HomeFragmentModel

sealed class HomeFragmentState {

    data class UserFetched(val userModel: UserModel) : HomeFragmentState()

    data class HomePageContents(val homeFragmentModel: HomeFragmentModel) : HomeFragmentState()

}
