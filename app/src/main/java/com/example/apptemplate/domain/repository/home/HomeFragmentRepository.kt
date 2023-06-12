package com.example.apptemplate.domain.repository.home

import com.example.apptemplate.data.remote.model.home.HomeFragmentModel
import dev.mridx.common.common_data.domain.repository.BaseRepository

interface HomeFragmentRepository : BaseRepository {

    suspend fun getHomeFragmentContents(): HomeFragmentModel

}