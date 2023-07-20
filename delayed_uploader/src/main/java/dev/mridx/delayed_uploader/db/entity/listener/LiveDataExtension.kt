package dev.mridx.delayed_uploader.db.entity.listener

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.work.WorkInfo

interface LiveDataExtension {

    fun getData(context: Context): LiveData<WorkInfo>

}