package com.example.apptemplate.presentation.auth.fragment.logout

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.apptemplate.R
import com.example.apptemplate.databinding.LogoutDialogBinding
import com.example.apptemplate.presentation.auth.fragment.logout.event.LogoutFragmentEvent
import com.example.apptemplate.presentation.auth.fragment.logout.state.LogoutFragmentState
import com.example.apptemplate.presentation.auth.fragment.logout.vm.LogoutFragmentViewModel
import com.example.apptemplate.presentation.routing.activity.RoutingActivity
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import dev.mridx.common.common_utils.utils.errorSnackbar
import dev.mridx.common.common_utils.utils.startActivity
import dev.mridx.common.common_presentation.dialog.rounded_dialog.RoundedDialogFragment
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LogoutFragment : RoundedDialogFragment() {


    private var binding_: LogoutDialogBinding? = null
    private val binding get() = binding_!!


    private val viewModel by viewModels<LogoutFragmentViewModel>()

    private var currentSnackbar: Snackbar? = null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding_ = LogoutDialogBinding.inflate(inflater, container, false).apply {
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
            }
        }

        binding.logoutBtn.setOnClickListener {
            //
            handleLogout()
        }
        binding.cancelBtn.setOnClickListener {
            //
            if (!isCancelable) return@setOnClickListener
            dismiss()
        }


    }

    private fun handleState(state: LogoutFragmentState) {
        when (state) {
            is LogoutFragmentState.LogoutResponse -> {
                handleLogoutResponse(state)
            }
        }
    }


    private fun handleLogoutResponse(state: LogoutFragmentState.LogoutResponse) {
        if (state.response.isFailed()) {
            //
            isCancelable = true
            currentSnackbar?.dismiss()
            currentSnackbar = errorSnackbar(
                binding.root,
                message = getString(
                    R.string.staticErrorMessage
                ),
                duration = Snackbar.LENGTH_LONG,
                action = "OK"
            )
            return
        }
        startActivity(RoutingActivity::class.java)
        requireActivity().finish()
    }


    private fun hideProgressbar() {
        binding.progressbar.isVisible = false
        binding.logoutBtn.isVisible = true
    }

    private fun handleLogout() {
        isCancelable = false
        binding.apply {
            logoutBtn.isVisible = false
            progressbar.isVisible = true
        }
        viewModel.addEvent(event = LogoutFragmentEvent.Logout)
    }


    override fun onDestroyView() {
        binding_ = null
        super.onDestroyView()
    }


}