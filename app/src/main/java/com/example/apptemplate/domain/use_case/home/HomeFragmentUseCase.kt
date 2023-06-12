package com.example.apptemplate.domain.use_case.home

import com.example.apptemplate.data.remote.model.home.HomeFragmentModel
import com.example.apptemplate.domain.repository.home.HomeFragmentRepository
import javax.inject.Inject

class HomeFragmentUseCase @Inject constructor(
    private val homeFragmentRepository: HomeFragmentRepository
) {


    suspend fun getFragmentContents(): HomeFragmentModel {
        return homeFragmentRepository.getHomeFragmentContents()
    }

}