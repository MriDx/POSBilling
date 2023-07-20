package dev.mridx.delayed_uploader.db.entity.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey


@Entity(ParameterDataModel.TABLE_NAME)
class ParameterDataModel {

    companion object {
        const val TABLE_NAME = "parameter_data_"
    }

    @PrimaryKey(autoGenerate = true)
    var id: Int = 0

    @ColumnInfo(name = "parameter")
    var parameter: String

    @ColumnInfo(name = "job_id")
    var jobId: String

    constructor(id: Int, parameter: String, jobId: String) {
        this.id = id
        this.parameter = parameter
        this.jobId = jobId
    }

    @Ignore
    constructor(parameter: String, jobId: String) {
        this.parameter = parameter
        this.jobId = jobId
    }


}