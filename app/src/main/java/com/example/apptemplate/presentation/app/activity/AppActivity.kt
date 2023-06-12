package com.example.apptemplate.presentation.app.activity

import android.os.Bundle
import android.view.MenuItem
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.onNavDestinationSelected
import androidx.navigation.ui.setupWithNavController
import com.example.apptemplate.R
import com.example.apptemplate.databinding.AppActivityBinding
import dagger.hilt.android.AndroidEntryPoint
import dev.mridx.common_presentation.activity.base.BaseActivity


@AndroidEntryPoint
class AppActivity : BaseActivity() {


    private lateinit var binding: AppActivityBinding

    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding =
            DataBindingUtil.setContentView<AppActivityBinding?>(this, R.layout.app_activity).apply {
                lifecycleOwner = this@AppActivity
            }

        setSupportActionBar(binding.appBar.toolbar)

        setupNavigation()

    }

    private fun setupNavigation() {
        navController = findNavController(R.id.fragmentContainer)

        appBarConfiguration = AppBarConfiguration(navController.graph)


        binding.appBar.toolbar.setupWithNavController(
            navController = navController, configuration = appBarConfiguration
        )

        //binding.bottomNavigationView.setupWithNavController(navController)

        /*navController.addOnDestinationChangedListener { controller, destination, arguments ->
            TransitionManager.beginDelayedTransition(binding.bottomNavigationView, Fade())
            when (destination.id) {
                R.id.homeFragment, R.id.meetingsFragment, R.id.schemeListFragment, R.id.noticeHolderFragment -> {
                    binding.bottomNavigationView.isVisible = true
                }
                else -> {
                    binding.bottomNavigationView.isVisible = false
                }
            }
        }*/
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