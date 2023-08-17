package com.example.apptemplate.domain.repository.file

import dev.mridx.common.common_data.data.remote.model.FileUploadResponseModel
import dev.mridx.common.common_data.data.remote.model.NetworkResource
import dev.mridx.common.common_data.domain.repository.BaseRepository

interface FileRepository : BaseRepository {

    suspend fun uploadFile(filePath: String): NetworkResource<FileUploadResponseModel>

}