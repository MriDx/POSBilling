package dev.techmess.gym_mem.data.remote.repository.home

import android.content.SharedPreferences
import dev.mridx.common.common_data.di.qualifier.AppPreference
import dev.techmess.gym_mem.R
import dev.techmess.gym_mem.data.constants.ROLE
import dev.techmess.gym_mem.data.local.model.action.ActionModel
import dev.techmess.gym_mem.data.remote.model.home.HomeFragmentModel
import dev.techmess.gym_mem.domain.repository.home.HomeFragmentRepository
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

                val actions = listOf(
                    ActionModel(
                        heading = "Add Member",
                        icon = R.drawable.user_plus,
                        supportingText = "Add new member to Gym",
                        id = "new_member"
                    ),
                    ActionModel(
                        heading = "Manage Members",
                        icon = R.drawable.users_four,
                        supportingText = "View or Manage members",
                        id = "members"
                    ),
                    ActionModel(
                        heading = "Payments",
                        icon = R.drawable.wallet,
                        supportingText = "View or Manage payments",
                        id = "payments"
                    ),
                )

                homeFragmentModel.actions = actions



            } catch (e: Exception) {
                e.printStackTrace()
            }
            homeFragmentModel

        }
    }


}