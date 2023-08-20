package dev.techmess.gym_mem.presentation.app.fragment.member.add

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import dagger.hilt.android.AndroidEntryPoint
import dev.mridx.common.common_presentation.fragment.base.BaseFragment
import dev.techmess.gym_mem.databinding.AddMemberFragmentBinding
import dev.techmess.gym_mem.presentation.app.fragment.member.add.event.AddMemberFragmentEvent
import dev.techmess.gym_mem.presentation.app.fragment.member.add.state.AddMemberFragmentState
import dev.techmess.gym_mem.presentation.app.fragment.member.add.vm.AddMemberFragmentViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AddMemberFragment : BaseFragment<AddMemberFragmentBinding>() {


    private val viewModel by viewModels<AddMemberFragmentViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding_ = AddMemberFragmentBinding.inflate(inflater, container, false).apply {
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

        viewModel.addEvent(event = AddMemberFragmentEvent.FetchFormContents)


    }

    private fun handleState(state: AddMemberFragmentState) {
        when (state) {
            is AddMemberFragmentState.FormContents -> {
                handleFormContents(state)
            }
        }
    }

    private fun handleFormContents(state: AddMemberFragmentState.FormContents) {

        binding.formBuilder.buildFromFieldList(
            fragment = this,
            fields = state.addMemberFragmentModel.fields
        )

    }


}