package dev.techmess.gym_mem.presentation.app.fragment.settings.vm

import androidx.lifecycle.viewModelScope
import dev.techmess.gym_mem.domain.use_case.settings.GetSettingsFragmentActionsUseCase
import dev.techmess.gym_mem.presentation.app.fragment.settings.event.SettingsFragmentEvent
import dev.techmess.gym_mem.presentation.app.fragment.settings.state.SettingsFragmentState
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.mridx.common.common_presentation.vm.base.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SettingsFragmentViewModel @Inject constructor(
    private val getSettingsFragmentActionsUseCase: GetSettingsFragmentActionsUseCase,
) : BaseViewModel<SettingsFragmentEvent, SettingsFragmentState>() {


    override fun addEvent(event: SettingsFragmentEvent) {
        when (event) {
            is SettingsFragmentEvent.FetchActions -> {
                fetchActions()
            }
        }
    }

    private fun fetchActions() {
        viewModelScope.launch(Dispatchers.IO) {
            val response = getSettingsFragmentActionsUseCase.execute()
            sendState(state = SettingsFragmentState.SettingsActions(actions = response.actions))
        }
    }


}