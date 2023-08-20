package dev.techmess.gym_mem.domain.repository.member

import dev.mridx.common.common_data.domain.repository.BaseRepository
import dev.techmess.gym_mem.data.remote.model.member.AddMemberFragmentModel

interface MemberRepository : BaseRepository {


    suspend fun fetchAddMemberForm(): AddMemberFragmentModel


}