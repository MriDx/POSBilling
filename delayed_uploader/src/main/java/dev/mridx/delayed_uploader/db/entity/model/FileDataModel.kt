package dev.mridx.delayed_uploader.db.entity.model

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Ignore
import androidx.room.PrimaryKey
import androidx.work.WorkInfo
import androidx.work.WorkManager
import dev.mridx.delayed_uploader.db.entity.listener.LiveDataExtension
import java.util.UUID


/**
 * table contains only files information
 */
@Entity(
    FileDataModel.TABLE_NAME, foreignKeys = arrayOf(
        ForeignKey(
            entity = ParameterDataModel::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("parameter_id")
        )
    )
)
class FileDataModel : LiveDataExtension {

    companion object {
        const val TABLE_NAME = "file_data_"
    }

    /**
     * primary key of the table to be auto generated
     */
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0

    /**
     * job id for the current file upload
     * it will be used later in progress tracking
     */
    @ColumnInfo(name = "job_id")
    var jobId: String


    /**
     * file path of the file to be uploaded
     */
    @ColumnInfo(name = "file_path")
    var filePath: String


    /**
     * string to be used as param during uploading of the file
     */
    @ColumnInfo(name = "upload_param")
    var uploadParam: String


    /**
     * boolean value if the given file is uploaded or not
     */
    @ColumnInfo(name = "is_uploaded")
    var isUploaded: Boolean = false


    /**
     * uploaded file name received from server after uploading the file
     */
    @ColumnInfo(name = "uploaded_name")
    var uploadedName: String? = null


    /**
     * this table if related to ParamInfoTable
     * thus primary key from that table is used
     * as foreign key in this table
     */
    @ColumnInfo(name = "parameter_id")
    var parameterId: Int = 0

    constructor(
        id: Int,
        jobId: String,
        filePath: String,
        uploadParam: String,
        isUploaded: Boolean,
        uploadedName: String?,
        parameterId: Int
    ) {
        this.id = id
        this.jobId = jobId
        this.filePath = filePath
        this.uploadParam = uploadParam
        this.isUploaded = isUploaded
        this.uploadedName = uploadedName
        this.parameterId = parameterId
    }

    @Ignore
    constructor(
        jobId: String,
        filePath: String,
        uploadParam: String,
        isUploaded: Boolean,
        uploadedName: String?,
        parameterId: Int
    ) {
        this.jobId = jobId
        this.filePath = filePath
        this.uploadParam = uploadParam
        this.isUploaded = isUploaded
        this.uploadedName = uploadedName
        this.parameterId = parameterId
    }


    override fun getData(context: Context): LiveData<WorkInfo> {
        if (jobId.isEmpty()) throw Exception("File upload is not scheduled yet !")
        return WorkManager.getInstance(context).getWorkInfoByIdLiveData(UUID.fromString(jobId))
    }


}
