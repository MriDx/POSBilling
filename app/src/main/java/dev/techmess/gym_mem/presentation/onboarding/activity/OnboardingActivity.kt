package dev.techmess.gym_mem.presentation.onboarding.activity

import android.os.Bundle
import android.view.MenuItem
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.onNavDestinationSelected
import dagger.hilt.android.AndroidEntryPoint
import dev.mridx.common.common_presentation.activity.BaseActivity
import dev.techmess.gym_mem.R
import dev.techmess.gym_mem.databinding.OnboardingActivityBinding

@AndroidEntryPoint
class OnboardingActivity : BaseActivity() {


    private lateinit var binding: OnboardingActivityBinding

    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        binding = DataBindingUtil.setContentView<OnboardingActivityBinding?>(this, R.layout.onboarding_activity).apply {
            lifecycleOwner = this@OnboardingActivity
        }


        setupNavigation()

    }



    private fun setupNavigation() {
        navController = findNavController(R.id.fragmentContainer)

        appBarConfiguration = AppBarConfiguration(navController.graph)


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