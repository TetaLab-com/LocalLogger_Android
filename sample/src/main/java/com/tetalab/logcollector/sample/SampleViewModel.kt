package com.tetalab.logcollector.sample

import android.app.Application
import com.tetalab.logcollector.coroutine.ioJob
import com.tetalab.logcollector.data.source.LogsDataSource
import com.tetalab.logcollector.data.source.SessionDataSource
import com.tetalab.logcollector.ui.base.BaseViewModel
import kotlinx.coroutines.CoroutineScope

class SampleViewModel constructor(
    application: Application,
    coroutineScope: CoroutineScope
) : BaseViewModel(application, coroutineScope) {

    init {
        ioJob {
            SessionDataSource.createNewSession()
        }
    }

    fun wLog(message: String) = ioJob {
        LogsDataSource.w("", "", "Message2")
    }

    fun wLog(className: String, method: String, message: String) = ioJob {
        LogsDataSource.w("testClass2", "testMessage2", "Message2")
    }

    fun iLog(message: String) = ioJob {
        LogsDataSource.i("", "", "Message2")
    }

    fun iLog(className: String, method: String, message: String) = ioJob {
        LogsDataSource.i("testClass2", "testMessage2", "Message2")
    }

    fun eLog(message: String) = ioJob {
        LogsDataSource.e("", "", "Message2")
    }

    fun eLog(className: String, method: String, message: String) = ioJob {
        LogsDataSource.e("testClass2", "testMessage2", "Message2")
    }

    fun inMessageLog(message: String) = ioJob {
        LogsDataSource.inMessage("", "", "Message2")
    }

    fun inMessageLog(className: String, method: String, message: String) = ioJob {
        LogsDataSource.inMessage("testClass2", "testMessage2", "Message2")
    }

    fun outMessageLog(message: String) = ioJob {
        LogsDataSource.outMessage("", "", "Message2")
    }

    fun outMessageLog(className: String, method: String, message: String) = ioJob {
        LogsDataSource.outMessage("testClass2", "testMessage2", "Message2")
    }
}