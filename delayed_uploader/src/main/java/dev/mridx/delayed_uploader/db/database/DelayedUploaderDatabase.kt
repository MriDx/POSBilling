package dev.mridx.delayed_uploader.db.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import dev.mridx.delayed_uploader.db.dao.DelayedUploaderDao
import dev.mridx.delayed_uploader.db.entity.model.FileDataModel
import dev.mridx.delayed_uploader.db.entity.model.JobDataModel
import dev.mridx.delayed_uploader.db.entity.model.NotificationDataModel
import dev.mridx.delayed_uploader.db.entity.model.ParameterDataModel


@Database(
    entities = [
        JobDataModel::class,
        ParameterDataModel::class,
        FileDataModel::class,
        NotificationDataModel::class
    ],
    version = 2,
    exportSchema = true
)
abstract class DelayedUploaderDatabase : RoomDatabase() {


    companion object {
        private var instance_: DelayedUploaderDatabase? = null
        val instance get() = instance_!!

        /**
         * creates singleton database instance
         */
        @Synchronized
        fun initialize(context: Context): DelayedUploaderDatabase {
            if (instance_ == null) {
                instance_ = Room.databaseBuilder(
                    context.applicationContext,
                    DelayedUploaderDatabase::class.java,
                    "delayed_uploader_db*"
                ).fallbackToDestructiveMigration()
                    .build()
            }
            return instance
        }
    }

    abstract fun delayedUploaderDao(): DelayedUploaderDao


}