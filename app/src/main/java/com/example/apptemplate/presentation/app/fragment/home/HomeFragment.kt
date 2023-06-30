package com.example.apptemplate.presentation.app.fragment.home

import android.Manifest
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.apptemplate.R
import com.example.apptemplate.data.local.model.action.ActionModel
import com.example.apptemplate.databinding.ActionCardSmallHorizontalBinding
import com.example.apptemplate.databinding.HomeFragmentBinding
import com.example.apptemplate.presentation.app.fragment.home.event.HomeFragmentEvent
import com.example.apptemplate.presentation.app.fragment.home.state.HomeFragmentState
import com.example.apptemplate.presentation.app.fragment.home.vm.HomeFragmentViewModel
import com.example.apptemplate.presentation.app.fragment.notification.permission.NotificationPermissionFragment
import dagger.hilt.android.AndroidEntryPoint
import dev.mridx.common.common_data.di.qualifier.PermissionPreference
import dev.mridx.common.common_utils.constants.Permissions
import dev.mridx.common.common_utils.presentation_utils.PlaceHolderDrawableHelper
import dev.mridx.common_presentation.fragment.base.BaseFragment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject


@AndroidEntryPoint
class HomeFragment : BaseFragment<HomeFragmentBinding>() {

    private val viewModel by viewModels<HomeFragmentViewModel>()


    @Inject
    @PermissionPreference
    lateinit var permissionSharedPreference: SharedPreferences


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding_ = HomeFragmentBinding.inflate(inflater, container, false).apply {
            lifecycleOwner = viewLifecycleOwner
        }
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.state.collectLatest { state ->
                        handleViewState(state)
                    }
                }
                launch {
                    viewModel.addEvent(event = HomeFragmentEvent.FetchUser)
                }
                launch {
                    withContext(Dispatchers.Main) {
                        binding.swipeRefreshLayout.isRefreshing = true
                    }
                    viewModel.addEvent(event = HomeFragmentEvent.FetchContents)
                }
            }
        }


        val menuHost: MenuHost = requireActivity()

        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.home_menu, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                when (menuItem.itemId) {
                    R.id.notificationBtn -> {

                    }
                }
                return true
            }
        }, viewLifecycleOwner)


        /*binding.surveyBtn.setOnClickListener {
            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToHoardingSurveyFragment())
        }*/

        checkNotificationPermission()


    }

    private fun checkNotificationPermission() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) {
            return
        }

        Log.d("mridx", "checkNotificationPermission: ${permissionSharedPreference.all}")

        if (permissionSharedPreference.getBoolean(
                Permissions.NOTIFICATION_PERMISSION_PROMPT,
                false
            )
        ) {
            return
        }
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            NotificationPermissionFragment().show(childFragmentManager, "")
        }
    }

    private fun handleViewState(state: HomeFragmentState) {
        when (state) {
            is HomeFragmentState.UserFetched -> {
                renderUserDetails(state)
            }

            is HomeFragmentState.HomePageContents -> {
                handleHomePageContents(state)
            }
        }
    }

    private fun renderUserDetails(state: HomeFragmentState.UserFetched) {
        Glide.with(requireContext()).asBitmap().load(state.userModel.photo).placeholder(
            PlaceHolderDrawableHelper.getAvatar(
                requireContext(), state.userModel.name, 0
            )
        ).into(binding.avatar)

        binding.userNameView.text = state.userModel.name
        binding.designationView.text = state.userModel.designation

        binding.userInfoCard.setOnClickListener {
            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToSettingsFragment())
        }

    }

    private fun handleHomePageContents(state: HomeFragmentState.HomePageContents) {
        binding.swipeRefreshLayout.isRefreshing = false
        renderActions(actions = state.homeFragmentModel.actions)
        //renderBanners()
    }

    private fun renderActions(actions: List<ActionModel>) {
        binding.actionHolder.apply {
            setItemCount(actions.size)
            itemBuilder = { parent, index ->
                DataBindingUtil.inflate<ActionCardSmallHorizontalBinding>(
                    LayoutInflater.from(parent.context),
                    R.layout.action_card_small_horizontal,
                    parent,
                    false
                ).root
            }
            itemBinding { holder, index ->
                val action = actions[index]
                DataBindingUtil.bind<ActionCardSmallHorizontalBinding>(holder.itemView)?.apply {
                    titleView.text = action.heading
                    root.setOnClickListener {
                        handleActionClicked(actionModel = action)
                    }
                }
            }
            layoutManager = LinearLayoutManager(requireContext())
        }.render()
    }


    private fun handleActionClicked(actionModel: ActionModel) {
        when (actionModel.id) {
            /* "hoarding_survey" -> {
                 findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToHoardingSurveyFragment())
             }*/
        }
    }

}