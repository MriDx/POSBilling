package com.example.apptemplate.presentation.app.fragment.settings.vm

import com.example.apptemplate.presentation.app.fragment.settings.event.SettingsFragmentEvent
import com.example.apptemplate.presentation.app.fragment.settings.state.SettingsFragmentState
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.mridx.common_presentation.vm.base.BaseViewModel
import javax.inject.Inject


@HiltViewModel
class SettingsFragmentViewModel @Inject constructor(): BaseViewModel<SettingsFragmentEvent, SettingsFragmentState>() {


    override fun addEvent(event: SettingsFragmentEvent) {
        TODO("Not yet implemented")
    }



}