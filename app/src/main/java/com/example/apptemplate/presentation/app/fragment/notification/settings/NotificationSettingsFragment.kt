package com.example.apptemplate.presentation.app.fragment.notification.settings

import android.Manifest
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.edit
import com.example.apptemplate.BuildConfig
import com.example.apptemplate.databinding.NotificationSettingsFragmentBinding
import dagger.hilt.android.AndroidEntryPoint
import dev.mridx.common.common_data.di.qualifier.PermissionPreference
import dev.mridx.common.common_utils.constants.Permissions
import dev.mridx.common.common_presentation.fragment.base.BaseFragment
import javax.inject.Inject

@AndroidEntryPoint
class NotificationSettingsFragment : BaseFragment<NotificationSettingsFragmentBinding>() {


    @Inject
    @PermissionPreference
    lateinit var permissionSharedPreference: SharedPreferences


    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) {
            if (it) {
                //this.dismiss()
                handleNotificationPermissionEnabled()
            }
        }

    private val appNotificationSettingsLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            //Log.d("mridx", ": $it")
            //binding.notificationSwitch.isChecked = it.resultCode == Activity.RESULT_OK
            checkNotificationPermissions()
        }


    private fun handleNotificationPermissionEnabled() {
        permissionSharedPreference.edit {
            putBoolean(Permissions.NOTIFICATION_ENABLED, true)
        }
        binding.notificationSwitch.isChecked = true
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding_ = NotificationSettingsFragmentBinding.inflate(inflater, container, false).apply {
            lifecycleOwner = viewLifecycleOwner
        }
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        checkNotificationPermissions()


        binding.notificationSwitch.setOnCheckedChangeListener { buttonView, isChecked ->
            handleNotificationSwitch(isChecked)
        }

    }


    private fun handleNotificationSwitch(isChecked: Boolean) {

        if (!isChecked) {
            permissionSharedPreference.edit {
                putBoolean(Permissions.NOTIFICATION_ENABLED, isChecked)
            }
            return
        }

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) {
            permissionSharedPreference.edit {
                putBoolean(Permissions.NOTIFICATION_ENABLED, isChecked)
            }
            return
        }

        binding.notificationSwitch.isChecked = false

        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.POST_NOTIFICATIONS
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            //
            binding.notificationSwitch.isChecked = true
            permissionSharedPreference.edit {
                putBoolean(Permissions.NOTIFICATION_ENABLED, true)
            }
            return
        }

        if (ActivityCompat.shouldShowRequestPermissionRationale(
                requireActivity(),
                Manifest.permission.POST_NOTIFICATIONS
            )
        ) {
            //show rational
            appNotificationSettingsLauncher.launch(Intent(Settings.ACTION_APP_NOTIFICATION_SETTINGS).apply {
                putExtra(Settings.EXTRA_APP_PACKAGE, BuildConfig.APPLICATION_ID)
            })
            return
        }

        if (permissionSharedPreference.getBoolean(Permissions.NOTIFICATION_PERMISSION, false)) {
            //already asked,
            appNotificationSettingsLauncher.launch(Intent(Settings.ACTION_APP_NOTIFICATION_SETTINGS).apply {
                putExtra(Settings.EXTRA_APP_PACKAGE, BuildConfig.APPLICATION_ID)
            })
            return
        }

        permissionSharedPreference.edit {
            putBoolean(Permissions.NOTIFICATION_PERMISSION, true)
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
        }

    }

    private fun checkNotificationPermissions() {
        val notificationEnabled =
            permissionSharedPreference.getBoolean(Permissions.NOTIFICATION_ENABLED, false)

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) {
            binding.notificationSwitch.isChecked = notificationEnabled
            return
        }

        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.POST_NOTIFICATIONS
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            if (notificationEnabled) {
                binding.notificationSwitch.isChecked = true
            }
            return
        }
        permissionSharedPreference.edit {
            putBoolean(Permissions.NOTIFICATION_ENABLED, false)
        }
        binding.notificationSwitch.isChecked = false
    }


}