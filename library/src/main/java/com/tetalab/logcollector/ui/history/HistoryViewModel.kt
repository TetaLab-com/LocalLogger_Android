package com.tetalab.logcollector.ui.history

import android.app.Application
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

    val sessions = MutableLiveData<List<Session>>()

    fun getSessions() = ioJob {
        val database = LogDatabase.getInstance()
        val dao = database.sessionDao()

        val sessionList = dao.getAll()
        sessions.postValue(sessionList)
    }
}