package dev.techmess.gym_mem.presentation.onboarding.fragment.gym_registration

import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.edit
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import dev.mridx.common.common_data.di.qualifier.AppPreference
import dev.mridx.common.common_presentation.dialog.bottom_loading.BottomLoadingDialog
import dev.mridx.common.common_presentation.fragment.base.BaseFragment
import dev.techmess.gym_mem.data.constants.GYM_NAME
import dev.techmess.gym_mem.data.constants.GYM_REGISTERED
import dev.techmess.gym_mem.data.constants.USER_FULL_NAME
import dev.techmess.gym_mem.databinding.GymRegistrationFragmentBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@AndroidEntryPoint
class GymRegistrationFragment : BaseFragment<GymRegistrationFragmentBinding>() {

    @Inject
    @AppPreference
    lateinit var appSharedPreference: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding_ = GymRegistrationFragmentBinding.inflate(inflater, container, false).apply {
            lifecycleOwner = viewLifecycleOwner
        }
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.submitBtn.setOnClickListener {
            handleSubmitClicked()
        }


    }

    private fun handleSubmitClicked() {

        currentDialog?.dismiss()
        currentDialog = BottomLoadingDialog.Builder().show(childFragmentManager, "")

        val gymName = binding.gymNameField.text.toString()
        val name = binding.nameField.text.toString()

        var valid = true
        if (gymName.isEmpty()) {
            binding.gymNameFieldLayout.error = "Enter your Gym name !"
            valid = false
        }
        if (name.isEmpty()) {
            binding.nameFieldLayout.error = "Enter your full name !"
            valid = false
        }

        if (!valid) {
            currentDialog?.dismiss()
            return
        }

        //save details

        lifecycleScope.launch(Dispatchers.IO) {

            appSharedPreference.edit {
                putString(USER_FULL_NAME, name)
                putString(GYM_NAME, gymName)
                putBoolean(GYM_REGISTERED, true)
            }

            withContext(Dispatchers.Main) {

                currentDialog?.dismiss()
                findNavController().navigate(GymRegistrationFragmentDirections.actionGymRegistrationFragmentToAppActivity2())
                requireActivity().finish()
                return@withContext

            }


        }


    }


}