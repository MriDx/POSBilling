package com.example.apptemplate.presentation.auth.fragment.login_fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.text.bold
import androidx.core.text.buildSpannedString
import androidx.core.text.underline
import androidx.fragment.app.viewModels
import com.example.apptemplate.R
import com.example.apptemplate.databinding.LoginFragmentBinding
import com.example.apptemplate.presentation.auth.fragment.login_fragment.event.LoginFragmentEvent
import com.example.apptemplate.presentation.auth.fragment.login_fragment.vm.LoginFragmentViewModel
import dagger.hilt.android.AndroidEntryPoint
import dev.mridx.common.common_data.di.qualifier.BaseUrl
import dev.mridx.common_presentation.dialog.bottom_loading.BottomLoadingDialog
import dev.mridx.common_presentation.fragment.base.BaseFragment
import javax.inject.Inject


@AndroidEntryPoint
class LoginFragment : BaseFragment<LoginFragmentBinding>() {


    @Inject
    @BaseUrl
    lateinit var baseUrl: String

    private val viewModel by viewModels<LoginFragmentViewModel>()

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
            /*findNavController().navigate(
                LoginFragmentDirections.actionLoginFragmentToWebViewFragment2(
                    title = "Terms and Privacy Policy",
                    webViewModel = WebViewModel(url = "${baseUrl}privacy-policy")
                )
            )*/
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
            deviceName = "app"
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


}