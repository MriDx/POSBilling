package dev.techmess.gym_mem.presentation.app.fragment.member.add.state

import dev.techmess.gym_mem.data.remote.model.member.AddMemberFragmentModel

sealed class AddMemberFragmentState {


    data class FormContents(
        val addMemberFragmentModel: AddMemberFragmentModel,
    ) : AddMemberFragmentState()


}
