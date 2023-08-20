package dev.techmess.gym_mem.presentation.app.fragment.member.add.vm

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.mridx.common.common_presentation.vm.base.BaseViewModel
import dev.techmess.gym_mem.domain.use_case.member.MemberUseCase
import dev.techmess.gym_mem.presentation.app.fragment.member.add.event.AddMemberFragmentEvent
import dev.techmess.gym_mem.presentation.app.fragment.member.add.state.AddMemberFragmentState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddMemberFragmentViewModel @Inject constructor(
    private val memberUseCase: MemberUseCase,
) :
    BaseViewModel<AddMemberFragmentEvent, AddMemberFragmentState>() {


    override fun addEvent(event: AddMemberFragmentEvent) {
        when (event) {
            is AddMemberFragmentEvent.FetchFormContents -> {
                fetchFormContents(event)
            }
        }
    }

    private fun fetchFormContents(event: AddMemberFragmentEvent.FetchFormContents) {
        viewModelScope.launch(Dispatchers.IO) {
            val response = memberUseCase.getFormContents()
            sendState(
                state = AddMemberFragmentState.FormContents(
                    addMemberFragmentModel = response
                )
            )
        }
    }


}