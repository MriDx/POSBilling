package dev.mridx.delayed_uploader.db.entity.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey


@Entity(NotificationDataModel.TABLE_NAME)
class NotificationDataModel {

    companion object {
        const val TABLE_NAME = "notification_data_"
    }


    @PrimaryKey(autoGenerate = true)
    var id: Int = 0

    @ColumnInfo(name = "job_id")
    var jobId: String

    @ColumnInfo(name = "notification_id")
    var notificationId: Int = 0

    @ColumnInfo(name = "title")
    var title: String

    @ColumnInfo(name = "body")
    var body: String

    @ColumnInfo(name = "auto_cancel")
    var autoCancel: Boolean = true

    @ColumnInfo(name = "vibrate")
    var vibrate: Boolean = true

    @ColumnInfo(name = "sound")
    var sound: Boolean = true

    @ColumnInfo(name = "icon")
    var icon: Int = -1

    constructor(
        id: Int,
        jobId: String,
        notificationId: Int,
        title: String,
        body: String,
        autoCancel: Boolean,
        vibrate: Boolean,
        sound: Boolean,
        icon: Int
    ) {
        this.id = id
        this.jobId = jobId
        this.notificationId = notificationId
        this.title = title
        this.body = body
        this.autoCancel = autoCancel
        this.vibrate = vibrate
        this.sound = sound
        this.icon = icon
    }

    @Ignore
    constructor(
        jobId: String,
        notificationId: Int,
        title: String,
        body: String,
        autoCancel: Boolean,
        vibrate: Boolean = true,
        sound: Boolean = true,
        icon: Int = -1,
    ) {
        this.jobId = jobId
        this.notificationId = notificationId
        this.title = title
        this.body = body
        this.autoCancel = autoCancel
        this.vibrate = vibrate
        this.sound = sound
        this.icon = icon
    }


}
