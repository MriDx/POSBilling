package dev.mridx.delayed_uploader.db.entity.relation

import androidx.annotation.Keep
import androidx.room.Embedded
import androidx.room.Relation
import dev.mridx.delayed_uploader.db.entity.model.FileDataModel
import dev.mridx.delayed_uploader.db.entity.model.ParameterDataModel

@Keep
data class ParameterFilesModel(
    @Embedded var parameterDataModel: ParameterDataModel,
    @Relation(
        parentColumn = "id",
        entityColumn = "parameter_id",
    )
    var files: List<FileDataModel>
)
