package com.example.apptemplate.data.remote.repository.home

import android.content.SharedPreferences
import com.example.apptemplate.data.constants.ROLE
import com.example.apptemplate.data.remote.model.home.HomeFragmentModel
import com.example.apptemplate.domain.repository.home.HomeFragmentRepository
import dev.mridx.common.common_data.di.qualifier.AppPreference
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject


class HomeFragmentRepositoryImpl @Inject constructor(
    @AppPreference
    private val appSharedPreferences: SharedPreferences,
) : HomeFragmentRepository {


    override suspend fun getHomeFragmentContents(): HomeFragmentModel {
        return withContext(Dispatchers.IO) {

            val homeFragmentModel = HomeFragmentModel()

            try {

                val role = appSharedPreferences.getString(ROLE, "") ?: ""


            } catch (e: Exception) {
                e.printStackTrace()
            }
            homeFragmentModel

        }
    }


}