package com.example.apptemplate.presentation.auth.activity

import android.content.SharedPreferences
import android.os.Bundle
import android.view.MenuItem
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.onNavDestinationSelected
import androidx.navigation.ui.setupWithNavController
import androidx.transition.Fade
import androidx.transition.TransitionManager
import com.example.apptemplate.R
import com.example.apptemplate.databinding.AuthActivityBinding
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import dev.mridx.common.common_data.di.qualifier.AppPreference
import dev.mridx.common.common_utils.utils.infoSnackbar
import dev.mridx.common_presentation.activity.base.BaseActivity
import javax.inject.Inject


@AndroidEntryPoint
class AuthActivity : BaseActivity() {


    private lateinit var binding: AuthActivityBinding

    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration

    private var message: String? = null


    @AppPreference
    @Inject
    lateinit var appSharedPreferences: SharedPreferences


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        binding =
            DataBindingUtil.setContentView<AuthActivityBinding?>(this, R.layout.auth_activity)
                .apply {
                    setLifecycleOwner { lifecycle }
                }

        setSupportActionBar(binding.appbar.toolbar)

        message = intent?.getStringExtra("message")

        if (message != null && message!!.isNotEmpty()) {
            infoSnackbar(
                view = binding.root,
                message = message!!,
                duration = Snackbar.LENGTH_INDEFINITE,
                action = "OK"
            )
        }



        setupNavigation()


    }


    private fun setupNavigation() {

        navController = findNavController(R.id.fragmentContainer)

        appBarConfiguration = AppBarConfiguration.Builder().build()

        binding.appbar.toolbar.setupWithNavController(
            navController = navController,
            configuration = appBarConfiguration
        )

        navController.addOnDestinationChangedListener { controller, destination, arguments ->
            TransitionManager.beginDelayedTransition(binding.appbar.toolbar, Fade())
            when (destination.id) {
                R.id.loginFragment -> {
                    binding.appbar.toolbar.isVisible = false
                }

                else -> {
                    binding.appbar.toolbar.isVisible = true
                }
            }
        }

    }


    override fun onSupportNavigateUp(): Boolean {
        //return super.onSupportNavigateUp()
        if (!navController.navigateUp()) finish()
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return item.onNavDestinationSelected(navController) || super.onOptionsItemSelected(item)
    }


}