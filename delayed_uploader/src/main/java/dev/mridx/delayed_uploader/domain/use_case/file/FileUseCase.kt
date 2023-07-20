package dev.mridx.delayed_uploader.domain.use_case.file

import dev.mridx.common.common_data.data.remote.model.FileUploadResponseModel
import dev.mridx.common.common_data.data.remote.model.NetworkResource
import dev.mridx.delayed_uploader.domain.repository.DURepository
import okhttp3.MultipartBody
import javax.inject.Inject

class FileUseCase @Inject constructor(private val duRepository: DURepository) {

    suspend fun uploadFile(
        url: String,
        headers: Map<String, String>,
        bodyPart: MultipartBody.Part,
    ): NetworkResource<FileUploadResponseModel> {
        return duRepository.uploadFile(url = url, headers = headers, part = bodyPart)
    }

}