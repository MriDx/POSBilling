package dev.mridx.delayed_uploader.data.local.model

import androidx.annotation.Keep
import dev.mridx.delayed_uploader.db.entity.model.NotificationDataModel

@Keep
data class DUNotificationConfiguration(
    var notificationId: Int,
    var title: String,
    var body: String,
    var autoCancel: Boolean = true,
) {
    fun toNotificationDataModel(): NotificationDataModel {
        return NotificationDataModel(
            jobId = "",
            notificationId = notificationId,
            title = title,
            body = body,
            autoCancel = true,
        )
    }
}

