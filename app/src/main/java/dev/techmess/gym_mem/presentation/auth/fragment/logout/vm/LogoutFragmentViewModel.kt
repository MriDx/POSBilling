package dev.techmess.gym_mem.presentation.auth.fragment.logout.vm

import androidx.lifecycle.viewModelScope
import dev.techmess.gym_mem.domain.use_case.auth.AuthUseCase
import dev.techmess.gym_mem.presentation.auth.fragment.logout.event.LogoutFragmentEvent
import dev.techmess.gym_mem.presentation.auth.fragment.logout.state.LogoutFragmentState
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.mridx.common.common_presentation.vm.base.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LogoutFragmentViewModel @Inject constructor(
    private val authUseCase: AuthUseCase,
): BaseViewModel<LogoutFragmentEvent, LogoutFragmentState>() {



    override fun addEvent(event: LogoutFragmentEvent) {
        when (event) {
            is LogoutFragmentEvent.Logout -> {
                logoutUser()
            }
        }
    }


    private fun logoutUser() {
        viewModelScope.launch(Dispatchers.IO) {

            val response = authUseCase.logout()

            sendState(state = LogoutFragmentState.LogoutResponse(
                response = response,
                message = response.message
            ))

        }
    }


}