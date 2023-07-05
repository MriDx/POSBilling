package com.example.apptemplate.presentation.auth.fragment.login

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.text.bold
import androidx.core.text.buildSpannedString
import androidx.core.text.underline
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.apptemplate.R
import com.example.apptemplate.data.local.model.web_view.WebViewModel
import com.example.apptemplate.databinding.LoginFragmentBinding
import com.example.apptemplate.presentation.auth.fragment.login.event.LoginFragmentEvent
import com.example.apptemplate.presentation.auth.fragment.login.state.LoginFragmentState
import com.example.apptemplate.presentation.auth.fragment.login.vm.LoginFragmentViewModel
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.ktx.messaging
import com.google.gson.JsonObject
import dagger.hilt.android.AndroidEntryPoint
import dev.mridx.common.common_data.di.qualifier.BaseUrl
import dev.mridx.common.common_utils.utils.errorSnackbar
import dev.mridx.common.common_presentation.dialog.bottom_loading.BottomLoadingDialog
import dev.mridx.common.common_presentation.fragment.base.BaseFragment
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject


@AndroidEntryPoint
class LoginFragment : BaseFragment<LoginFragmentBinding>() {


    @Inject
    @BaseUrl
    lateinit var baseUrl: String

    private val viewModel by viewModels<LoginFragmentViewModel>()

    private var deviceToken = "app"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding_ = LoginFragmentBinding.inflate(inflater, container, false).apply {
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

        generateFCMToken()


        binding.welcomeText.text = buildSpannedString {
            append("Welcome \n")
            //bold { append("Login Here".uppercase()) }
            bold { append(getString(R.string.app_name)) }
        }

        binding.taglineText.text = "Building the nation with pride."

        binding.privacyPolicyBtn.apply {
            text = buildSpannedString {
                append("By Logging in You Accept ")
                underline { append("Terms of Use") }
                append(" and ")
                underline { append("Privacy Policy.") }
            }
        }.setOnClickListener {
            //
            findNavController().navigate(
                LoginFragmentDirections.actionLoginFragmentToWebViewFragment2(
                    title = "Terms and Privacy Policy",
                    webViewModel = WebViewModel(url = "${baseUrl}privacy-policy")
                )
            )
        }

        binding.poweredByView.text = buildSpannedString {
            append("Powered by ")
            bold { append("Sumato Globaltech Pvt. Ltd.") }
        }


        binding.loginBtn.setOnClickListener {
            //
            handleLoginClicked()
        }


    }

    private fun handleLoginClicked() {

        showLoading()

        val login = binding.loginField.text.toString().trim()
        val password = binding.passwordField.text.toString().trim()

        val event = LoginFragmentEvent.LoginUser(
            login = login,
            password = password,
            deviceName = deviceToken
        )

        if (!validateForm(event)) {
            hideLoading()
            return
        }

        viewModel.addEvent(event)

    }

    private fun showLoading() {
        currentDialog?.dismiss()
        currentDialog = BottomLoadingDialog.Builder()
            .cancelable(false)
            .show(childFragmentManager, "")
    }

    private fun hideLoading() {
        currentDialog?.dismiss()
    }

    private fun validateForm(event: LoginFragmentEvent.LoginUser): Boolean {
        var valid = true
        if (event.login.isEmpty()) {
            binding.loginFieldLayout.error = "Email or Login Id is blank."
            valid = false
        }
        if (event.password.isEmpty()) {
            binding.passwordFieldLayout.error = "Password is blank."
            valid = false
        }
        return valid
    }

    private fun handleState(state: LoginFragmentState) {
        when (state) {
            is LoginFragmentState.LoginResponse -> {
                handleLoginResponse(state)
            }
        }
    }

    private fun handleLoginResponse(state: LoginFragmentState.LoginResponse) {
        currentDialog?.dismiss()
        if (state.response.isFailed()) {
            if (state.response.data?.hasInlineErrors() == true) {
                renderInlineErrors(errors = state.response.data!!.getInlineErrors())
                return
            }
            currentSnackbar?.dismiss()
            currentSnackbar = errorSnackbar(
                binding.root,
                message = state.response.data?.errorsResponse?.message ?: state.response.message
                ?: state.message ?: "Something went wrong ! Try again later.",
                duration = Snackbar.LENGTH_INDEFINITE,
                action = "OK"
            )
            return
        }
        findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToAppActivity())
        requireActivity().finish()
    }

    private fun renderInlineErrors(errors: JsonObject) {
        errors.keySet().forEach { key ->
            val errorMessage = errors[key].asJsonArray[0].asString
            when (key) {
                "login" -> {
                    binding.loginFieldLayout.error = errorMessage
                }

                "password" -> {
                    binding.passwordFieldLayout.error = errorMessage
                }
            }
        }
    }

    private fun generateFCMToken() {
        Firebase.messaging.token.addOnCompleteListener {
            if (it.isSuccessful) {
                deviceToken = it.result
                Log.d("mridx", "fetchFirebaseMessagingId: $deviceToken")
            }
        }
    }


}