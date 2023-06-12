package com.example.apptemplate.domain.repository.user

import com.example.apptemplate.data.local.model.user.UserModel
import dev.mridx.common.common_data.domain.repository.BaseRepository

interface UserRepository : BaseRepository {

    suspend fun fetchLocalUser(): UserModel


}