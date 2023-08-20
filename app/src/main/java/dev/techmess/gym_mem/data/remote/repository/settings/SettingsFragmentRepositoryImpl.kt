package dev.techmess.gym_mem.data.remote.repository.settings

import dev.techmess.gym_mem.R
import dev.techmess.gym_mem.data.local.model.action.ActionModel
import dev.techmess.gym_mem.data.remote.model.settings.SettingsFragmentModel
import dev.techmess.gym_mem.domain.repository.settings.SettingsFragmentRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject


class SettingsFragmentRepositoryImpl @Inject constructor() : SettingsFragmentRepository {


    override suspend fun fetchActions(): SettingsFragmentModel {
        return withContext(Dispatchers.IO) {

            val settingsFragmentModel = SettingsFragmentModel()

            try {

                val actions = listOf(
                    ActionModel(
                        heading = "Profile",
                        icon = R.drawable.ic_outline_account_circle_24,
                        id = "profile",
                        supportingText = "View or manage your profile.",
                    ),
                    ActionModel(
                        heading = "Logout",
                        icon = R.drawable.ic_baseline_power_settings_new_24,
                        id = "logout",
                        supportingText = "Log out from your account.",
                    ),
                    ActionModel.blankModel(),
                    ActionModel(
                        heading = "Notification",
                        icon = R.drawable.baseline_notifications_none_24,
                        id = "notification",
                        supportingText = "Manage notification settings.",
                    ),
                    //ActionModel.blankModel(),
                    ActionModel(
                        heading = "Privacy Policy",
                        icon = R.drawable.ic_outline_privacy_tip_24,
                        id = "privacy_policy",
                        supportingText = "",
                    ),
                    ActionModel(
                        heading = "App Version",
                        icon = R.drawable.ic_outline_info_24,
                        id = "app_info",
                        supportingText = "1.2",
                    ),
                    ActionModel(
                        heading = "Powered by",
                        icon = R.drawable.ic_baseline_android_24,
                        id = "powered_by",
                        supportingText = "Sumato GlobalTech Pvt. Ltd.",
                    ),
                )

                settingsFragmentModel.actions = actions

            } catch (e: Exception) {
                e.printStackTrace()
            }

            settingsFragmentModel

        }
    }
}