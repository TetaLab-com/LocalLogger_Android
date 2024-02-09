package com.tetalab.logcollector.data.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.tetalab.logcollector.data.model.AppLog
import com.tetalab.logcollector.data.model.Session
import com.tetalab.logcollector.util.Const.DATABASE_NAME

@Database(
    entities = [AppLog::class, Session::class],
    version = 1
)

abstract class LogDatabase : RoomDatabase() {

    abstract fun appLogDao(): AppLogDAO
    abstract fun sessionDao(): SessionDAO

    companion object {

        @Volatile
        private var instance: LogDatabase? = null

        fun getInstance(): LogDatabase = instance!!

        fun buildDatabase(appContext: Context) {
            if (instance == null) {
                val builder =
                    Room.databaseBuilder(
                        appContext,
                        LogDatabase::class.java,
                        DATABASE_NAME
                    )

                instance = builder.build()
            }
        }
    }
}