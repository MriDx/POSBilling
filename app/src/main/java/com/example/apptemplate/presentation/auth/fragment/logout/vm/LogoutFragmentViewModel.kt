package com.example.apptemplate.presentation.auth.fragment.logout.vm

import com.example.apptemplate.presentation.auth.fragment.logout.event.LogoutFragmentEvent
import com.example.apptemplate.presentation.auth.fragment.logout.state.LogoutFragmentState
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.mridx.common_presentation.vm.base.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class LogoutFragmentViewModel @Inject constructor(): BaseViewModel<LogoutFragmentEvent, LogoutFragmentState>() {



    override fun addEvent(event: LogoutFragmentEvent) {
        TODO("Not yet implemented")
    }



}