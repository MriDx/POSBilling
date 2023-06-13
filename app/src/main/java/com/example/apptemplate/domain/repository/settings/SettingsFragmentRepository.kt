package com.example.apptemplate.domain.repository.settings

import com.example.apptemplate.data.remote.model.settings.SettingsFragmentModel
import dev.mridx.common.common_data.domain.repository.BaseRepository

interface SettingsFragmentRepository : BaseRepository {

    suspend fun FetchActions(): SettingsFragmentModel

}