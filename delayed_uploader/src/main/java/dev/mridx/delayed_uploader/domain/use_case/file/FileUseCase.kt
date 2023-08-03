package dev.mridx.delayed_uploader.domain.use_case.file

import dev.mridx.common.common_data.data.remote.model.FileUploadResponseModel
import dev.mridx.common.common_data.data.remote.model.NetworkResource
import dev.mridx.delayed_uploader.domain.repository.DURepository
import javax.inject.Inject

class FileUseCase @Inject constructor(private val duRepository: DURepository) {

    suspend fun uploadFile(
        url: String,
        headers: Map<String, String>,
        filePath: String,
        fileParam: String
    ): NetworkResource<FileUploadResponseModel> {
        return duRepository.uploadFile(
            url = url,
            headers = headers,
            filePath = filePath,
            fileParam = fileParam
        )
    }

    suspend fun uploadFileDirect(
        url: String,
        filePath: String,
        fileParam: String
    ): NetworkResource<FileUploadResponseModel> {
        return duRepository.uploadFileDirect(url = url, filePath = filePath, fileParam = fileParam)
    }

}