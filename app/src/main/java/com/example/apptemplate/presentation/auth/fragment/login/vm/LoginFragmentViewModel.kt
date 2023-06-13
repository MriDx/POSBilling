package com.example.apptemplate.presentation.auth.fragment.login.vm

import androidx.lifecycle.viewModelScope
import com.example.apptemplate.domain.use_case.auth.AuthUseCase
import com.example.apptemplate.presentation.auth.fragment.login.event.LoginFragmentEvent
import com.example.apptemplate.presentation.auth.fragment.login.state.LoginFragmentState
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.mridx.common_presentation.vm.base.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginFragmentViewModel @Inject constructor(
    private val authUseCase: AuthUseCase,
) : BaseViewModel<LoginFragmentEvent, LoginFragmentState>() {


    override fun addEvent(event: LoginFragmentEvent) {
        when (event) {
            is LoginFragmentEvent.LoginUser -> {
                handleLogin(event)
            }
        }
    }

    private fun handleLogin(event: LoginFragmentEvent.LoginUser) {
        viewModelScope.launch(Dispatchers.IO) {
            val response = authUseCase.emailPassLogin(event.toJson())

            sendState(
                state = LoginFragmentState.LoginResponse(
                    response = response,
                    message = response.message
                )
            )
        }
    }


}