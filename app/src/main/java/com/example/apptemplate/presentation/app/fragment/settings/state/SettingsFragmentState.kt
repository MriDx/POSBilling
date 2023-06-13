package com.example.apptemplate.presentation.app.fragment.settings.state

import com.example.apptemplate.data.local.model.action.ActionModel

sealed class SettingsFragmentState {

    data class SettingsActions(
        val actions: List<ActionModel> = emptyList()
    ) : SettingsFragmentState()

}