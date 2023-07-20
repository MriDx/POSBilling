package dev.mridx.delayed_uploader.db.entity.relation

import androidx.annotation.Keep
import dev.mridx.delayed_uploader.db.entity.model.JobDataModel
import dev.mridx.delayed_uploader.db.entity.model.NotificationDataModel

@Keep
data class DUJobDataModel(
    var jobData: JobDataModel,
    var jobParameters: List<ParameterFilesModel>,
    var jobNotification: NotificationDataModel? = null
)
