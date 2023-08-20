package dev.techmess.gym_mem.domain.use_case.settings

import dev.techmess.gym_mem.data.remote.model.settings.SettingsFragmentModel
import dev.techmess.gym_mem.domain.repository.settings.SettingsFragmentRepository
import javax.inject.Inject

class GetSettingsFragmentActionsUseCase @Inject constructor(private val settingsFragmentRepository: SettingsFragmentRepository) {


    suspend fun execute() : SettingsFragmentModel {
        return settingsFragmentRepository.fetchActions()
    }

}