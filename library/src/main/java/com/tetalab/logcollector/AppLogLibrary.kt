package com.tetalab.logcollector

import android.content.Context
import com.tetalab.logcollector.data.room.LogDatabase
import com.tetalab.logcollector.data.source.LogsDataSource
import com.tetalab.logcollector.data.source.SessionDataSource

class AppLogLibrary {
    companion object {
        fun init(context: Context) {
            LogDatabase.buildDatabase(context)

            LogsDataSource.init()
            SessionDataSource.init()
        }
    }
}