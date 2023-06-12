package com.example.apptemplate.presentation.app.fragment.home.vm

import androidx.lifecycle.viewModelScope
import com.example.apptemplate.domain.repository.user.UserRepository
import com.example.apptemplate.domain.use_case.home.HomeFragmentUseCase
import com.example.apptemplate.presentation.app.fragment.home.event.HomeFragmentEvent
import com.example.apptemplate.presentation.app.fragment.home.state.HomeFragmentState
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.mridx.common_presentation.vm.base.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeFragmentViewModel @Inject constructor(
    private val homeFragmentUseCase: HomeFragmentUseCase,
    private val userRepository: UserRepository,
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
            val userModel = userRepository.fetchLocalUser()

            sendState(state = HomeFragmentState.UserFetched(userModel))

        }
    }


}