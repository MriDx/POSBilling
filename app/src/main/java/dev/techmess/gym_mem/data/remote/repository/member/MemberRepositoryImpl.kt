package dev.techmess.gym_mem.data.remote.repository.member

import dev.mridx.dynamic_form.data.model.DynamicFieldModel
import dev.mridx.dynamic_form.utils.DynamicFieldType
import dev.techmess.gym_mem.data.remote.model.member.AddMemberFragmentModel
import dev.techmess.gym_mem.domain.repository.member.MemberRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MemberRepositoryImpl @Inject constructor() : MemberRepository {


    override suspend fun fetchAddMemberForm(): AddMemberFragmentModel {
        return withContext(Dispatchers.IO) {

            val fields = listOf(
                DynamicFieldModel(
                    type = DynamicFieldType.TEXT_INPUT,
                    heading = "Member's Name",
                    name = "member_name",
                    required = true,
                    hint = "Tony Stark",
                ),
                DynamicFieldModel(
                    type = DynamicFieldType.MOBILE_INPUT,
                    heading = "Mobile Number",
                    name = "mobile_number",
                    required = true,
                    hint = "XXXXXXXXXX",
                ),
                DynamicFieldModel(
                    type = DynamicFieldType.MOBILE_INPUT,
                    heading = "Emergency Content Number",
                    name = "emergency_content_number",
                    required = true,
                    hint = "XXXXXXXXXX",
                    prefix = "+91",
                    max_length = 10
                ),
                DynamicFieldModel(
                    type = DynamicFieldType.TEXT_INPUT,
                    heading = "Guardian's Name",
                    name = "guardian_name",
                    required = true,
                    hint = "Name of guardian",
                    prefix = "",
                ),
                DynamicFieldModel(
                    type = DynamicFieldType.DATE_INPUT,
                    heading = "Date of Birth",
                    name = "dob",
                    required = true,
                    hint = "Select member's dob",
                    prefix = "",
                ),
                DynamicFieldModel(
                    type = DynamicFieldType.MULTILINE_INPUT,
                    heading = "Address",
                    name = "address",
                    required = true,
                    hint = "",
                    prefix = "",
                ),
            )

            AddMemberFragmentModel(fields = fields)

        }
    }


}