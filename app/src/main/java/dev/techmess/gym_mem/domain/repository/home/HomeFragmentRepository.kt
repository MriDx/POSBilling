package dev.techmess.gym_mem.domain.repository.home

import dev.mridx.common.common_data.domain.repository.BaseRepository
import dev.techmess.gym_mem.data.remote.model.home.HomeFragmentModel

interface HomeFragmentRepository : BaseRepository {

    suspend fun getHomeFragmentContents(): HomeFragmentModel

}