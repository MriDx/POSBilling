package dev.techmess.gym_mem.domain.use_case.home

import dev.techmess.gym_mem.data.remote.model.home.HomeFragmentModel
import dev.techmess.gym_mem.domain.repository.home.HomeFragmentRepository
import javax.inject.Inject

class HomeFragmentUseCase @Inject constructor(
    private val homeFragmentRepository: HomeFragmentRepository
) {


    suspend fun getFragmentContents(): HomeFragmentModel {
        return homeFragmentRepository.getHomeFragmentContents()
    }

}