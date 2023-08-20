package dev.techmess.gym_mem.presentation.app.fragment.home.vm

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.mridx.common.common_presentation.vm.base.BaseViewModel
import dev.techmess.gym_mem.domain.use_case.home.HomeFragmentUseCase
import dev.techmess.gym_mem.domain.use_case.user.UserUseCase
import dev.techmess.gym_mem.presentation.app.fragment.home.event.HomeFragmentEvent
import dev.techmess.gym_mem.presentation.app.fragment.home.state.HomeFragmentState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeFragmentViewModel @Inject constructor(
    private val homeFragmentUseCase: HomeFragmentUseCase,
    private val userUseCase: UserUseCase,
) : BaseViewModel<HomeFragmentEvent, HomeFragmentState>() {


    override fun addEvent(event: HomeFragmentEvent) {
        when (event) {
            is HomeFragmentEvent.FetchUser -> {
                fetchLocalUser()
            }

            is HomeFragmentEvent.FetchContents -> {
                fetchHomePageContents(event)
            }
        }
    }


    private fun fetchHomePageContents(event: HomeFragmentEvent.FetchContents) {
        viewModelScope.launch(Dispatchers.IO) {

            val response = homeFragmentUseCase.getFragmentContents()

            sendState(state = HomeFragmentState.HomePageContents(homeFragmentModel = response))

        }
    }


    private fun fetchLocalUser() {
        viewModelScope.launch(Dispatchers.IO) {
            val userModel = userUseCase.getLocalUser()

            sendState(state = HomeFragmentState.UserFetched(userModel))

        }
    }


}