package dev.mridx.delayed_uploader.jobs

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import dev.mridx.delayed_uploader.data.constants.Constants
import dev.mridx.delayed_uploader.db.dao.DelayedUploaderDao
import dev.mridx.delayed_uploader.domain.use_case.file.FileUseCase
import dev.mridx.delayed_uploader.utils.getValueFromKey
import dev.mridx.delayed_uploader.utils.toHeadersMap
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File


@HiltWorker
class FileUploadJob @AssistedInject constructor(
    @Assisted val context: Context,
    @Assisted val workerParameters: WorkerParameters,
    private val fileUseCase: FileUseCase,
    private val delayedUploaderDao: DelayedUploaderDao,
) : CoroutineWorker(context.applicationContext, workerParameters) {


    override suspend fun doWork(): Result {
        return withContext(Dispatchers.IO) {

            try {

                val url = workerParameters.getValueFromKey<String>(Constants.URL)
                val headers =
                    workerParameters.getValueFromKey<String>(Constants.HERDERS).toHeadersMap()
                val filePath = workerParameters.getValueFromKey<String>(Constants.FILE_PATH)

                //get file data from local db

                /*val fileDataModel = DelayedUploaderDatabase.instance.delayedUploaderDao()
                    .getFileData(filePath = filePath)
                    ?: throw Exception("No file data found from local db !")*/

                val fileDataModel =
                    delayedUploaderDao.getFileData(filePath = filePath)
                        ?: throw Exception("No file data found from local db !")

                val fileToUpload = File(fileDataModel.filePath)

                if (!fileToUpload.exists()) {
                    throw Exception("File not found !")
                }

                /*val requestFile =
                    fileToUpload.asRequestBody("application/octet-stream".toMediaType())

                val multipartBody = MultipartBody.Part.createFormData(
                    filename = fileDataModel.uploadParam,
                    name = fileToUpload.name,
                    body = requestFile
                )*/

//                val tmpHeaders = headers
//
//                Log.d("mridx", "doWork: $tmpHeaders")
//
//                val headers111 = mapOf(
//                    "Accept" to "application/json",
//                    "Authorization" to "Bearer 191|vRgtimW6bL2ryrd3n5ZitBV2a1gHmDcHzcwjTrZQ"
//                )

//                val fileUploadResponse = fileUseCase.uploadFile(
//                    url = url, headers = tmpHeaders, bodyPart = multipartBody
//                )

                /**
                 * passing static header map as dynamic headers causing issue,
                 *
                 */

                // TODO: needs to fix

                val fileUploadHeader = mapOf(
                    "Accept" to "application/json"
                )

                val fileUploadResponse = fileUseCase.uploadFile(
                    url = url,
                    headers = fileUploadHeader,
                    filePath = fileDataModel.filePath,
                    fileParam = fileDataModel.uploadParam
                )

                if (fileUploadResponse.isFailed()) {
                    throw Exception()
                }

                fileDataModel.uploadedName = fileUploadResponse.data!!.url

                fileDataModel.isUploaded = true

                delayedUploaderDao
                    .updateFileData(fileDataModel = fileDataModel)

                Result.success()

            } catch (e: Exception) {
                e.printStackTrace()
                Result.failure()
            }

        }
    }


}