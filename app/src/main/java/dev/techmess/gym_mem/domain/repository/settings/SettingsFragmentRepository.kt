package dev.techmess.gym_mem.domain.repository.settings

import dev.techmess.gym_mem.data.remote.model.settings.SettingsFragmentModel
import dev.mridx.common.common_data.domain.repository.BaseRepository

interface SettingsFragmentRepository : BaseRepository {

    suspend fun fetchActions(): SettingsFragmentModel

}