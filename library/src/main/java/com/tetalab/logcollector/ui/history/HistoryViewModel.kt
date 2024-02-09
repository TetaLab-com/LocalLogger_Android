package com.tetalab.logcollector.ui.history

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.tetalab.logcollector.coroutine.ioJob
import com.tetalab.logcollector.data.model.Session
import com.tetalab.logcollector.data.room.LogDatabase
import com.tetalab.logcollector.ui.base.BaseViewModel
import kotlinx.coroutines.CoroutineScope

class HistoryViewModel constructor(
    application: Application,
    coroutineScope: CoroutineScope
) : BaseViewModel(application, coroutineScope) {

    private val _sessions = MutableLiveData<List<Session>>()
    val sessions: LiveData<List<Session>> = _sessions

    fun getSessions() = ioJob {
        val database = LogDatabase.getInstance()
        val dao = database.sessionDao()

        val sessionList = dao.getAll()
        _sessions.postValue(sessionList)
    }
}