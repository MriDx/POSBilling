package com.example.apptemplate.presentation.app.fragment.notification.permission

import android.Manifest
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.content.edit
import com.example.apptemplate.BuildConfig
import com.example.apptemplate.databinding.NotificationPermissionFragmentBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import dev.mridx.common.common_data.di.qualifier.PermissionPreference
import dev.mridx.common.common_utils.constants.Permissions
import dev.mridx.common.common_utils.utils.POSITIVE_BTN
import dev.mridx.common.common_utils.utils.appSettings
import dev.mridx.common.common_utils.utils.showDialog
import javax.inject.Inject

@AndroidEntryPoint
class NotificationPermissionFragment : BottomSheetDialogFragment() {


    private var binding_: NotificationPermissionFragmentBinding? = null
    private val binding get() = binding_!!

    @Inject
    @PermissionPreference
    lateinit var permissionSharedPreference: SharedPreferences

    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) {
            if (it) {
                this.dismiss()
            }
        }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding_ = NotificationPermissionFragmentBinding.inflate(inflater, container, false).apply {
            lifecycleOwner = viewLifecycleOwner
        }
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        isCancelable = false


        binding.inBtn.setOnClickListener {
            checkPermission()
        }

        binding.skipBtn.setOnClickListener {
            permissionSharedPreference.edit {
                putBoolean(Permissions.NOTIFICATION_PERMISSION_PROMPT, true)
            }
            dismiss()
        }


    }

    private fun checkPermission() {

        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {


            //permission not granted
            //check if need to show rational

            /*if (ActivityCompat.shouldShowRequestPermissionRationale(
                    requireActivity(),
                    Manifest.permission.POST_NOTIFICATIONS
                )
            ) {
                //show notification permission rational

                return
            }*/

            Log.d("mridx", "checkPermission: ${permissionSharedPreference.all}")

            if (permissionSharedPreference.getBoolean(Permissions.NOTIFICATION_PERMISSION, false)) {

                //ask to enable from app settings
                showDialog(
                    title = "Action Required!",
                    message = "Enable Notification Permission from App Setting",
                    positiveBtn = "App Settings",
                    negativeBtn = "Skip",
                    showNegativeBtn = true,
                    cancellable = false
                ) { d, i ->
                    if (i == POSITIVE_BTN) {
                        //
                        appSettings(packageName = BuildConfig.APPLICATION_ID)
                    } else {
                        // store that user skipped the prompt
                        permissionSharedPreference.edit {
                            putBoolean(Permissions.NOTIFICATION_PERMISSION_PROMPT, true)
                        }
                    }
                    d.dismiss()
                }.show()
                return

            }

            permissionSharedPreference.edit {
                putBoolean(Permissions.NOTIFICATION_PERMISSION, true)
            }

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }

        }

    }


    override fun onDestroyView() {
        binding_ = null
        super.onDestroyView()
    }


}