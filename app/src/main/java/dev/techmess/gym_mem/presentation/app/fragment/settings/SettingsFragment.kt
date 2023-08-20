package dev.techmess.gym_mem.presentation.app.fragment.settings

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
import androidx.navigation.fragment.findNavController
import dev.techmess.gym_mem.R
import dev.techmess.gym_mem.data.local.model.action.ActionModel
import dev.techmess.gym_mem.data.local.model.web_view.WebViewModel
import dev.techmess.gym_mem.databinding.DividerItemViewBinding
import dev.techmess.gym_mem.databinding.SettingsFragmentBinding
import dev.techmess.gym_mem.databinding.SettingsItemViewBinding
import dev.techmess.gym_mem.presentation.app.fragment.settings.event.SettingsFragmentEvent
import dev.techmess.gym_mem.presentation.app.fragment.settings.state.SettingsFragmentState
import dev.techmess.gym_mem.presentation.app.fragment.settings.vm.SettingsFragmentViewModel
import dagger.hilt.android.AndroidEntryPoint
import dev.mridx.common.common_data.di.qualifier.BaseUrl
import dev.mridx.common.common_utils.utils.isNotNull
import dev.mridx.common.common_presentation.fragment.base.BaseFragment
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject


@AndroidEntryPoint
class SettingsFragment : BaseFragment<SettingsFragmentBinding>() {


    @Inject
    @BaseUrl
    lateinit var baseUrl: String


    private val viewModel by viewModels<SettingsFragmentViewModel>()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding_ = SettingsFragmentBinding.inflate(inflater, container, false).apply {
            setLifecycleOwner { viewLifecycleOwner.lifecycle }
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
                    viewModel.addEvent(event = SettingsFragmentEvent.FetchActions)
                }
            }
        }


    }

    private fun handleState(state: SettingsFragmentState) {
        when (state) {
            is SettingsFragmentState.SettingsActions -> {
                handleSettingsActions(state)
            }
        }
    }

    private fun handleSettingsActions(state: SettingsFragmentState.SettingsActions) {
        renderActions(state.actions)
    }

    private fun renderActions(actions: List<ActionModel>) {
        binding.settingsGroupOne.apply {
            setItemCount(actions.size)
            itemBuilder = { parent, index ->
                val actionModel = actions[index]
                if (actionModel.isBlank) {
                    DataBindingUtil.inflate<DividerItemViewBinding>(
                        LayoutInflater.from(parent.context),
                        R.layout.divider_item_view,
                        parent,
                        false
                    ).root
                } else {
                    DataBindingUtil.inflate<SettingsItemViewBinding>(
                        LayoutInflater.from(parent.context),
                        R.layout.settings_item_view,
                        parent,
                        false
                    ).root
                }
            }
            itemBinding { holder, index ->
                val actionModel = actions[index]
                if (!actionModel.isBlank) {
                    DataBindingUtil.bind<SettingsItemViewBinding>(holder.itemView)?.apply {
                        headingView.text = actionModel.heading
                        iconView.setImageResource(actionModel.icon)

                        subHeadingView.apply {
                            text = actionModel.supportingText
                        }.isVisible = actionModel.supportingText.isNotNull()

                        root.setOnClickListener {
                            handleActionItemClicked(actionModel)
                        }
                    }
                }
            }
        }.render()
    }

    private fun handleActionItemClicked(actionModel: ActionModel) {
        when (actionModel.id) {
            "profile" -> {
                findNavController().navigate(SettingsFragmentDirections.actionSettingsFragmentToProfileFragment())
            }

            "logout" -> {
                //show logout confirmation button
                findNavController().navigate(SettingsFragmentDirections.actionSettingsFragmentToLogoutFragment())
            }

            "notification" -> {
                findNavController().navigate(SettingsFragmentDirections.actionSettingsFragmentToNotificationSettingsFragment())
            }

            "privacy_policy" -> {
                findNavController().navigate(
                    SettingsFragmentDirections.actionSettingsFragmentToWebViewFragment(
                        title = getString(R.string.settingsFragmentPrivacyPolicy),
                        webViewModel = WebViewModel(url = "${baseUrl}privacy-policy")
                    )
                )
            }
        }
    }


}