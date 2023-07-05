package dev.mridx.common.common_presentation.fragment.permission_launcher

import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.activity.result.ActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import com.google.android.gms.common.api.ResolvableApiException
import dev.mridx.common.common_data.data.local.model.gps_result.GpsResult
import dev.mridx.common.common_presentation.fragment.base.BaseFragment

abstract class PermissionLauncherFragment<ViewBinding> : BaseFragment<ViewBinding>() {


    /**
     * Launches Runtime Permissions Request dialog
     * can request multiple permissions at a time
     *
     * usage
     * requestPermissionsLauncher.launch(arrayOf(permissions))
     *
     * handle permissions results
     * override permissionsResults function
     *
     */
    private val requestPermissionsLauncher =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { grantedPermissions ->
            permissionsResults(permissions = grantedPermissions)
        }

    /**
     * override this function to handle permission request results
     * @param permissions
     *
     */
    abstract fun permissionsResults(permissions: Map<String, Boolean>)

    /**
     * request permissions by calling this function
     *
     * @param permissions
     *
     */
    open fun requestPermissions(permissions: Array<String>) {
        requestPermissionsLauncher.launch(permissions)
    }


    /**
     *
     * Launches App settings activity to allow manual permission
     *
     * usage
     * appSettingsLauncher.launch(Intent().apply {
    action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
    data = Uri.fromParts("package", BuildConfig.APPLICATION_ID, null)
    })
     *
     * override onAppSettingsActivityResult to grab the result
     */
    private val appSettingsLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            onAppSettingsActivityResult(it)
        }

    /**
     * override to handle app settings activity result
     *
     * @param activityResult
     *
     */
    abstract fun onAppSettingsActivityResult(activityResult: ActivityResult)

    open fun launchAppSettings(packageName: String) {
        appSettingsLauncher.launch(Intent().apply {
            action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
            data = Uri.fromParts("package", packageName, null)
        })
    }


    private val gpsExceptionResolverLauncher =
        registerForActivityResult(ActivityResultContracts.StartIntentSenderForResult()) {
            onGpsExceptionActivityResult(it)
        }

    /**
     * override this function to handle gps exception resolve activity result.
     *
     * @param activityResult
     *
     */
    abstract fun onGpsExceptionActivityResult(activityResult: ActivityResult)

    /**
     * this is handle the gps exception
     */
    open fun handleGPSException(gpsResult: GpsResult): Boolean {
        var handled = false
        (gpsResult.exception as? ResolvableApiException)?.resolution?.intentSender?.let {
            handled = true
            gpsExceptionResolverLauncher.launch(IntentSenderRequest.Builder(it).build())
        }
        return handled
    }


}


