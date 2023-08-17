package com.example.apptemplate.domain.use_case.file

import com.example.apptemplate.domain.repository.file.FileRepository
import dev.mridx.common.common_data.data.remote.model.FileUploadResponseModel
import dev.mridx.common.common_data.data.remote.model.NetworkResource
import javax.inject.Inject

class FileUseCase @Inject constructor(
    private val fileRepository: FileRepository
) {

    suspend fun uploadFile(filePath: String): NetworkResource<FileUploadResponseModel> {
        return fileRepository.uploadFile(filePath)
    }

}