package dev.techmess.gym_mem.presentation.app.fragment.settings.state

import dev.techmess.gym_mem.data.local.model.action.ActionModel

sealed class SettingsFragmentState {

    data class SettingsActions(
        val actions: List<ActionModel> = emptyList()
    ) : SettingsFragmentState()

}