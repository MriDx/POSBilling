package dev.mridx.delayed_uploader.data.local.model

import androidx.annotation.Keep
import dev.mridx.delayed_uploader.data.constants.JOB_STATUS
import dev.mridx.delayed_uploader.db.entity.model.FileDataModel
import dev.mridx.delayed_uploader.db.entity.model.JobDataModel
import dev.mridx.delayed_uploader.db.entity.model.NotificationDataModel
import dev.mridx.delayed_uploader.db.entity.model.ParameterDataModel
import dev.mridx.delayed_uploader.db.entity.relation.DUJobDataModel
import dev.mridx.delayed_uploader.db.entity.relation.ParameterFilesModel
import org.json.JSONObject

@Keep
data class DUJob(
    val finalParameters: JSONObject? = null,
    val fileParameters: List<DUFileParameter>,
    val networkConfiguration: DUNetworkConfiguration,
    val clearAfter: Boolean = false,
    val notificationConfiguration: DUNotificationConfiguration,
) {


    private fun toJobDataModel(): JobDataModel {
        return JobDataModel(
            jobId = "",
            jobStatus = JOB_STATUS.PENDING,
            jobParams = finalParameters?.toString(),
            clearAfterSubmit = clearAfter
        )
    }


    private fun toJobParameters(): List<ParameterFilesModel> {
        return fileParameters.map { duFileParameter ->
            val parameterDataModel = ParameterDataModel(
                parameter = duFileParameter.parameter,
                jobId = ""
            )
            val fileDataModelList = duFileParameter.files.map { duFile ->
                FileDataModel(
                    jobId = "",
                    filePath = duFile.filePath,
                    uploadParam = duFile.parameter,
                    isUploaded = false,
                    uploadedName = null,
                    parameterId = 0
                )
            }
            ParameterFilesModel(
                parameterDataModel = parameterDataModel,
                files = fileDataModelList
            )
        }
    }


    private fun toNotificationDataModel(): NotificationDataModel {
        return notificationConfiguration.toNotificationDataModel()
    }

    fun toDUJobDataModel(): DUJobDataModel {

        val jobDataModel = toJobDataModel()

        val jobParameterModel = toJobParameters()

        return DUJobDataModel(
            jobData = jobDataModel,
            jobParameters = jobParameterModel,
            jobNotification = toNotificationDataModel()
        )

    }


}
