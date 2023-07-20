package dev.mridx.delayed_uploader.db.entity.model

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import androidx.work.WorkInfo
import androidx.work.WorkManager
import dev.mridx.delayed_uploader.db.entity.listener.LiveDataExtension
import java.util.UUID


@Entity(JobDataModel.TABLE_NAME)
class JobDataModel  : LiveDataExtension{

    companion object {
        const val TABLE_NAME = "job_data_"
    }

    @PrimaryKey(autoGenerate = true)
    var id = 0

    @ColumnInfo(name = "job_id")
    var jobId: String

    @ColumnInfo(name = "job_status")
    var jobStatus: String

    @ColumnInfo(name = "job_params")
    var jobParams: String? = null

    @ColumnInfo(name = "clear_after_submit")
    var clearAfterSubmit: Boolean = false


    constructor(
        id: Int,
        jobId: String,
        jobStatus: String,
        jobParams: String?,
        clearAfterSubmit: Boolean
    ) {
        this.id = id
        this.jobId = jobId
        this.jobStatus = jobStatus
        this.jobParams = jobParams
        this.clearAfterSubmit = clearAfterSubmit
    }

    @Ignore
    constructor(jobId: String, jobStatus: String, jobParams: String?, clearAfterSubmit: Boolean) {
        this.jobId = jobId
        this.jobStatus = jobStatus
        this.jobParams = jobParams
        this.clearAfterSubmit = clearAfterSubmit
    }


    override fun getData(context: Context): LiveData<WorkInfo> {
        if (jobId.isEmpty()) throw Exception("Job is not scheduled yet !")
        return WorkManager.getInstance(context).getWorkInfoByIdLiveData(UUID.fromString(jobId))
    }


}
