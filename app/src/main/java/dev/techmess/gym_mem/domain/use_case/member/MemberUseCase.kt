package dev.techmess.gym_mem.domain.use_case.member

import dev.techmess.gym_mem.data.remote.model.member.AddMemberFragmentModel
import dev.techmess.gym_mem.domain.repository.member.MemberRepository
import javax.inject.Inject

class MemberUseCase @Inject constructor(private val memberRepository: MemberRepository) {

    suspend fun getFormContents(): AddMemberFragmentModel {
        return memberRepository.fetchAddMemberForm()
    }

}