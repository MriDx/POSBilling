package dev.mridx.delayed_uploader.db.entity.relation

import androidx.annotation.Keep
import androidx.room.Embedded
import androidx.room.Relation
import dev.mridx.delayed_uploader.db.entity.model.JobDataModel
import dev.mridx.delayed_uploader.db.entity.model.ParameterDataModel

@Keep
data class JobParameterModel(
    @Embedded val jobDataModel: JobDataModel,
    @Relation(
        parentColumn = "job_id",
        entityColumn = "job_id",
    )
    val parameters: List<ParameterDataModel>,
)