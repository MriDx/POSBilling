package com.example.apptemplate.presentation.user.fragment.profile.vm

import androidx.lifecycle.viewModelScope
import com.example.apptemplate.domain.use_case.user.UserUseCase
import com.example.apptemplate.presentation.user.fragment.profile.event.ProfileFragmentEvent
import com.example.apptemplate.presentation.user.fragment.profile.state.ProfileFragmentState
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.mridx.common.common_presentation.vm.base.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ProfileFragmentViewModel @Inject constructor(
    private val userUseCase: UserUseCase,
) : BaseViewModel<ProfileFragmentEvent, ProfileFragmentState>() {


    override fun addEvent(event: ProfileFragmentEvent) {
        when (event) {
            is ProfileFragmentEvent.FetchUser -> {
                fetchLocalUser()
            }
        }
    }

    private fun fetchLocalUser() {
        viewModelScope.launch(Dispatchers.IO) {
            val userModel = userUseCase.getLocalUser()

            sendState(state = ProfileFragmentState.UserFetched(userModel))

        }
    }


}