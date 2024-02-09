package com.tetalab.logcollector.ui.log

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.lifecycleScope
import com.tetalab.logcollector.coroutine.ioJob
import com.tetalab.logcollector.data.model.AppLog
import com.tetalab.logcollector.data.model.Session
import com.tetalab.logcollector.data.source.LogsDataSource
import com.tetalab.logcollector.data.source.SessionDataSource
import com.tetalab.logcollector.ui.base.BaseViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LogViewModel constructor(
    application: Application,
    coroutineScope: CoroutineScope
) : BaseViewModel(application, coroutineScope) {

    private val _logs = MutableLiveData<List<AppLog>>()
    val logs: LiveData<List<AppLog>> = _logs

    private val _session = MutableLiveData<Session>()
    val session: LiveData<Session> = _session

    private var sessionId: Int = -1
    private var isActiveSession = true

    // Coroutine listening for Logs
    private var logUpdatesJob: Job? = null

    fun onStart() {
        if (!isActiveSession) return
        logUpdatesJob = launch {
            LogsDataSource.logsFlow.collect {
                withContext(Dispatchers.Main) {
                    _logs.postValue(it)
                }
            }
        }
    }

    fun onStop() {
        // Stop collecting when the View goes to the background
        logUpdatesJob?.cancel()
    }

    fun setSession(sessionId: Int) {
        this.sessionId = sessionId
        val activeSession = SessionDataSource.getActiveSession()
        _session.postValue(activeSession)
        isActiveSession = sessionId == activeSession?.uid

        //load historic data
        if (!isActiveSession) {
            ioJob {
                val logs = LogsDataSource.getHistoryLogs(sessionId)
                _logs.postValue(logs)
            }
        }
    }
}