package dev.mridx.delayed_uploader.jobs

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import dev.mridx.common.common_utils.utils.toJSONArrayOfString
import dev.mridx.delayed_uploader.data.constants.Constants
import dev.mridx.delayed_uploader.data.constants.JOB_STATUS
import dev.mridx.delayed_uploader.db.dao.DelayedUploaderDao
import dev.mridx.delayed_uploader.domain.use_case.job.DUJobUseCase
import dev.mridx.delayed_uploader.utils.NotificationUtils
import dev.mridx.delayed_uploader.utils.getValueFromKey
import dev.mridx.delayed_uploader.utils.toHeadersMap
import dev.mridx.delayed_uploader.utils.toJSON
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@HiltWorker
class SubmissionJob @AssistedInject constructor(
    @Assisted
    private val context: Context,
    @Assisted private val workerParameters: WorkerParameters,
    private val delayedUploaderDao: DelayedUploaderDao,
    private val duJobUseCase: DUJobUseCase,
) : CoroutineWorker(
    context.applicationContext,
    workerParameters
) {

    override suspend fun doWork(): Result {
        return withContext(Dispatchers.IO) {

            try {

                val url = workerParameters.getValueFromKey<String>(Constants.URL)
                val headers =
                    workerParameters.getValueFromKey<String>(Constants.HERDERS).toHeadersMap()

                val duJobDataModel = delayedUploaderDao.getDuJobDataModel(jobId = id.toString())

                val submissionParameters = duJobDataModel.jobData.jobParams.toJSON()

                duJobDataModel.jobParameters.map { parameterFilesModel ->
                    val fileParameter = parameterFilesModel.parameterDataModel.parameter
                    val files = parameterFilesModel.files.map { fileDataModel ->
                        fileDataModel.uploadedName ?: ""
                    }.toList()
                    submissionParameters.put(fileParameter, files.toJSONArrayOfString { it })
                }


                /*val response = duJobUseCase.submitJob(
                    url = url,
                    headers = headers,
                    params = submissionParameters
                )*/


                val response = duJobUseCase.submitJobDirect(
                    url = url,
                    params = submissionParameters
                )

                val jobDataModel = duJobDataModel.jobData

                if (response.isFailed()) {
                    jobDataModel.jobStatus = JOB_STATUS.FAILURE
                    // NotificationUtils.newNotification(context = context, data = duJobDataModel.jobNotification)
                    throw Exception()
                }

                //update that the job success
                //clear files and all if required
                //notify user about the successful operation


                jobDataModel.jobStatus = JOB_STATUS.SUCCESS

                delayedUploaderDao.updateJob(jobDataModel = duJobDataModel.jobData)

                duJobDataModel.jobNotification?.let { notificationDataModel ->
                    NotificationUtils.newNotification(
                        context = context,
                        data = notificationDataModel
                    )
                }

                Result.success()


            } catch (e: Exception) {
                e.printStackTrace()

                Result.failure()
            }
        }
    }

}