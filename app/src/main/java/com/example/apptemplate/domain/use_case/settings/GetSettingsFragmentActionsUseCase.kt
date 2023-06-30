package com.example.apptemplate.domain.use_case.settings

import com.example.apptemplate.data.remote.model.settings.SettingsFragmentModel
import com.example.apptemplate.domain.repository.settings.SettingsFragmentRepository
import javax.inject.Inject

class GetSettingsFragmentActionsUseCase @Inject constructor(private val settingsFragmentRepository: SettingsFragmentRepository) {


    suspend fun execute() : SettingsFragmentModel {
        return settingsFragmentRepository.fetchActions()
    }

}