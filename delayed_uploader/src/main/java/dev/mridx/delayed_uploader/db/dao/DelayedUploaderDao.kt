package dev.mridx.delayed_uploader.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import dev.mridx.delayed_uploader.db.entity.model.FileDataModel
import dev.mridx.delayed_uploader.db.entity.model.JobDataModel
import dev.mridx.delayed_uploader.db.entity.model.NotificationDataModel
import dev.mridx.delayed_uploader.db.entity.model.ParameterDataModel
import dev.mridx.delayed_uploader.db.entity.relation.DUJobDataModel
import dev.mridx.delayed_uploader.db.entity.relation.JobParameterModel
import dev.mridx.delayed_uploader.db.entity.relation.ParameterFilesModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.withContext

@Dao
interface DelayedUploaderDao {


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveUploaderJob(jobDataModel: JobDataModel): Long

    @Query("DELETE FROM ${JobDataModel.TABLE_NAME} WHERE job_id == :jobId")
    suspend fun deleteJobById(jobId: String)

    @Query("SELECT * FROM ${JobDataModel.TABLE_NAME} WHERE job_id == :jobId")
    suspend fun getJobById(jobId: String): JobDataModel

    @Query("SELECT * FROM ${JobDataModel.TABLE_NAME}")
    suspend fun getAllJobs(): List<JobDataModel>

    @Query("SELECT * FROM ${JobDataModel.TABLE_NAME} WHERE job_status == :jobStatus")
    suspend fun getJobsByStatus(jobStatus: String): List<JobDataModel>

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateJob(jobDataModel: JobDataModel)


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveJobParameters(parameterDataModel: ParameterDataModel): Long

    @Query("DELETE FROM ${JobDataModel.TABLE_NAME} WHERE job_id == :jobId")
    suspend fun deleteParametersByJobId(jobId: String)


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveFileData(fileDataModel: FileDataModel): Long

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateFileData(fileDataModel: FileDataModel)

    @Query("SELECT * FROM ${FileDataModel.TABLE_NAME} WHERE file_path == :filePath")
    suspend fun getFileData(filePath: String): FileDataModel?

    @Delete
    suspend fun deleteFileData(fileDataModel: FileDataModel)


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveNotificationData(notificationDataModel: NotificationDataModel): Long

    @Query("SELECT * FROM ${NotificationDataModel.TABLE_NAME} WHERE job_id == :jobId")
    suspend fun getNotificationByJodId(jobId: String): NotificationDataModel?


    @Transaction
    suspend fun saveNewDUJob(duJobDataModel: DUJobDataModel) {
        withContext(Dispatchers.IO) {

            saveUploaderJob(jobDataModel = duJobDataModel.jobData)

            duJobDataModel.jobParameters.map { parameterFilesModel ->
                async {
                    parameterFilesModel.parameterDataModel.jobId = duJobDataModel.jobData.jobId
                    saveParameterFiles(parameterFilesModel = parameterFilesModel)
                }
            }.awaitAll()

            duJobDataModel.jobNotification?.let { notificationDataModel ->
                saveNotificationData(notificationDataModel = notificationDataModel)
            }

        }
    }

    @Transaction
    suspend fun saveParameterFiles(parameterFilesModel: ParameterFilesModel) {
        withContext(Dispatchers.IO) {
            val parameterId =
                saveJobParameters(parameterDataModel = parameterFilesModel.parameterDataModel)
            parameterFilesModel.files.map { fileDataModel ->
                async {
                    fileDataModel.parameterId = parameterId.toInt()
                    saveFileData(fileDataModel = fileDataModel)
                }
            }.awaitAll()
        }
    }

    @Transaction
    @Query("SELECT * FROM ${JobDataModel.TABLE_NAME} WHERE job_id == :jobId")
    suspend fun getJobParameter(jobId: String): JobParameterModel

    @Transaction
    @Query("SELECT * FROM ${ParameterDataModel.TABLE_NAME} WHERE id == :parameterId")
    suspend fun getParameterFilesModel(parameterId: Int): ParameterFilesModel

    @Transaction
    suspend fun getDuJobDataModel(jobId: String): DUJobDataModel {
        return withContext(Dispatchers.IO) {

            val jobParameterModel = getJobParameter(jobId = jobId)

            val jobParameters = jobParameterModel.parameters.map { parameterDataModel ->
                async {
                    getParameterFilesModel(parameterId = parameterDataModel.id)
                }
            }.awaitAll()

            val notificationDataModel = getNotificationByJodId(jobId = jobId)

            DUJobDataModel(
                jobData = jobParameterModel.jobDataModel,
                jobParameters = jobParameters,
                jobNotification = notificationDataModel
            )

        }
    }


    @Transaction
    suspend fun getAllTasks(): List<DUJobDataModel> {
        return withContext(Dispatchers.IO) {

            val jobs = getAllJobs().map { jobDataModel ->
                getDuJobDataModel(jobId = jobDataModel.jobId)
            } ?: emptyList()


            jobs

        }
    }


    @Transaction
    suspend fun getTasksByStatus(status: String) : List<DUJobDataModel> {
        return withContext(Dispatchers.IO) {
            val jobs = getJobsByStatus(jobStatus = status).map {jobDataModel ->
                getDuJobDataModel(jobId = jobDataModel.jobId)
            } ?: emptyList()
            jobs
        }
    }


}