package com.example.apptemplate.presentation.app.fragment.home.state

import com.example.apptemplate.data.local.model.user.UserModel
import com.example.apptemplate.data.remote.model.home.HomeFragmentModel

sealed class HomeFragmentState {

    data class UserFetched(val userModel: UserModel) : HomeFragmentState()

    data class HomePageContents(val homeFragmentModel: HomeFragmentModel) : HomeFragmentState()

}
