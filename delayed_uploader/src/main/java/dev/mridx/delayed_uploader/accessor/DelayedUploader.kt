package dev.mridx.delayed_uploader.accessor

import android.content.Context
import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequest
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import dev.mridx.delayed_uploader.data.constants.Constants
import dev.mridx.delayed_uploader.data.constants.JOB_STATUS
import dev.mridx.delayed_uploader.data.local.model.DUJob
import dev.mridx.delayed_uploader.db.dao.DelayedUploaderDao
import dev.mridx.delayed_uploader.db.entity.relation.DUJobDataModel
import dev.mridx.delayed_uploader.jobs.FileUploadJob
import dev.mridx.delayed_uploader.jobs.SubmissionJob
import dev.mridx.delayed_uploader.utils.toHeaderString
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DelayedUploader @Inject constructor(private val delayedUploaderDao: DelayedUploaderDao) {

    suspend fun scheduleNewJob(context: Context, duJob: DUJob) {

        withContext(Dispatchers.IO) {

            val submissionJob = OneTimeWorkRequestBuilder<SubmissionJob>().apply {
                setInputData(
                    workDataOf(
                        Constants.URL to duJob.networkConfiguration.submissionUrl,
                        Constants.HERDERS to duJob.networkConfiguration.headers.toHeaderString(),
                    )
                )
                setConstraints(Constraints.Builder().apply {
                    setRequiredNetworkType(NetworkType.CONNECTED)
                    setRequiresBatteryNotLow(true)
                }.build())
            }.build()


            val duJobDataModel = duJob.toDUJobDataModel()


            duJobDataModel.jobData.jobId = submissionJob.stringId

            duJobDataModel.jobNotification?.jobId = submissionJob.stringId

            duJobDataModel.jobParameters.forEach { parameterFilesModel ->
                parameterFilesModel.parameterDataModel.jobId = submissionJob.stringId
            }

            var fileUploadJobList = listOf<OneTimeWorkRequest>()

            duJobDataModel.jobParameters.forEach { parameterFilesModel ->

                fileUploadJobList = parameterFilesModel.files.map { fileDataModel ->

                    val fileUploadJob = OneTimeWorkRequestBuilder<FileUploadJob>().apply {
                        setInputData(
                            workDataOf(
                                Constants.URL to duJob.networkConfiguration.uploadUrl,
                                Constants.HERDERS to duJob.networkConfiguration.headers.toHeaderString(),
                                Constants.FILE_PATH to fileDataModel.filePath
                            )
                        )
                        setConstraints(Constraints.Builder().apply {
                            setRequiredNetworkType(NetworkType.CONNECTED)
                            setRequiresBatteryNotLow(true)
                        }.build())
                    }.build()

                    fileDataModel.jobId = fileUploadJob.stringId

                    fileUploadJob
                }
            }


            delayedUploaderDao.saveNewDUJob(duJobDataModel = duJobDataModel)

            WorkManager.getInstance(context).beginWith(fileUploadJobList).then(submissionJob)
                .enqueue()

            //  WorkManager.getInstance(context).enqueue(fileUploadJobList)


        }


    }


    suspend fun getAllJobs(): List<DUJobDataModel> {
        return withContext(Dispatchers.IO) {
            delayedUploaderDao.getAllTasks()
        }
    }

    suspend fun getAllPendingJobs(): List<DUJobDataModel> {
        return withContext(Dispatchers.IO) {
            delayedUploaderDao.getTasksByStatus(JOB_STATUS.PENDING)
        }
    }

}