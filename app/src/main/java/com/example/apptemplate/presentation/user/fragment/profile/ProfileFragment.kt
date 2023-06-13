package com.example.apptemplate.presentation.user.fragment.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.bumptech.glide.Glide
import com.example.apptemplate.R
import com.example.apptemplate.databinding.HorizontalInfoItemViewBinding
import com.example.apptemplate.databinding.ProfileFragmentBinding
import com.example.apptemplate.presentation.user.fragment.profile.event.ProfileFragmentEvent
import com.example.apptemplate.presentation.user.fragment.profile.state.ProfileFragmentState
import com.example.apptemplate.presentation.user.fragment.profile.vm.ProfileFragmentViewModel
import dagger.hilt.android.AndroidEntryPoint
import dev.mridx.common.common_utils.presentation_utils.PlaceHolderDrawableHelper
import dev.mridx.common_presentation.fragment.base.BaseFragment
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


@AndroidEntryPoint
class ProfileFragment : BaseFragment<ProfileFragmentBinding>() {


    private val viewModel by viewModels<ProfileFragmentViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding_ = ProfileFragmentBinding.inflate(inflater, container, false).apply {
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
                        handleState(state)
                    }
                }
                launch {
                    viewModel.addEvent(event = ProfileFragmentEvent.FetchUser)
                }
            }
        }


    }

    private fun handleState(state: ProfileFragmentState) {
        when (state) {
            is ProfileFragmentState.UserFetched -> {
                renderUserDetails(state)
            }

            else -> {

            }
        }
    }


    private fun renderUserDetails(state: ProfileFragmentState.UserFetched) {
        binding.apply {
            Glide.with(requireContext())
                .asBitmap()
                .load(state.userModel.photo)
                .placeholder(
                    PlaceHolderDrawableHelper.getAvatar(
                        requireContext(),
                        state.userModel.name,
                        0
                    )
                )
                .into(avatarView)
            userNameView.text = state.userModel.name.replaceFirstChar { it.uppercase() }
            //userInfoView.text = state.userModel.role.replaceFirstChar { it.uppercase() }
            userInfoView.text = state.userModel.designation.split("_")
                .joinToString(separator = " ") { f -> f.replaceFirstChar { i -> i.uppercase() } }


            profileInfoView.removeAllViews()
            val detailsMap = state.userModel.getMapForProfile()

            detailsMap.entries.forEachIndexed { index, item ->
                val itemView = DataBindingUtil.inflate<HorizontalInfoItemViewBinding>(
                    LayoutInflater.from(requireContext()),
                    R.layout.horizontal_info_item_view,
                    profileInfoView,
                    false
                ).apply {
                    headingView.text = item.key
                    valueView.text = item.value
                    hrView.isVisible = index.plus(1) != detailsMap.size
                }.root
                profileInfoView.addView(itemView)
            }


        }
    }


}