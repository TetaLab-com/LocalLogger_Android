package com.tetalab.logcollector.ui.base

import android.app.Application
import com.tetalab.logcollector.coroutine.ioJob
import com.tetalab.logcollector.data.source.LogsDataSource
import com.tetalab.logcollector.data.source.SessionDataSource
import kotlinx.coroutines.CoroutineScope

open class BaseLogViewModel constructor(
    application: Application,
    coroutineScope: CoroutineScope
) : BaseViewModel(application, coroutineScope) {

    fun createNewLogSession() = ioJob {
        SessionDataSource.createNewSession()
    }

    fun wLog(message: String) = ioJob {
        LogsDataSource.w(message)
    }

    fun wLog(className: String, method: String, message: String) = ioJob {
        LogsDataSource.w(className, method, message)
    }

    fun iLog(message: String) = ioJob {
        LogsDataSource.i(message)
    }

    fun iLog(className: String, method: String, message: String) = ioJob {
        LogsDataSource.i(className, method, message)
    }

    fun eLog(message: String) = ioJob {
        LogsDataSource.e(message)
    }

    fun eLog(className: String, method: String, message: String) = ioJob {
        LogsDataSource.e(className, method, message)
    }

    fun inMessageLog(message: String) = ioJob {
        LogsDataSource.inMessage(message)
    }

    fun inMessageLog(className: String, method: String, message: String) = ioJob {
        LogsDataSource.inMessage(className, method, message)
    }

    fun outMessageLog(message: String) = ioJob {
        LogsDataSource.outMessage(message)
    }

    fun outMessageLog(className: String, method: String, message: String) = ioJob {
        LogsDataSource.outMessage(className, method, message)
    }
}