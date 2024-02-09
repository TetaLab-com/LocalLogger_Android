package com.tetalab.logcollector.data.source

import com.tetalab.logcollector.coroutine.ioTask
import com.tetalab.logcollector.data.model.Level
import com.tetalab.logcollector.data.model.AppLog
import com.tetalab.logcollector.data.model.Session
import com.tetalab.logcollector.data.room.AppLogDAO
import com.tetalab.logcollector.data.room.LogDatabase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import java.text.SimpleDateFormat
import java.util.Collections
import java.util.Date

/*
- we will need to review https://developer.android.com/topic/architecture/data-layer
 */
class LogsDataSource {

    companion object {
        private lateinit var session: Session
        private val logs = Collections.synchronizedList(mutableListOf<AppLog>())
        private var formatter: SimpleDateFormat = SimpleDateFormat("HH:mm:ssSSSS")

        private lateinit var dao: AppLogDAO

        fun init() {
            dao = LogDatabase.getInstance().appLogDao()
        }

        private suspend fun addLog(log: AppLog) {
            log.sessionId = session.uid
            logs.add(log)
            android.util.Log.d("AppLog", "[${log.className}] [${log.methodName}] ${log.message}")
            ioTask {
                dao.insertAll(log)
            }
            notifyListUpdates()
        }

        suspend fun w(className: String, methodName: String, message: String) {
            addLog(AppLog(getTimeString(), message, Level.WARNING, className, methodName))
        }

        suspend fun w(message: String) {
            w("", "", message)
        }

        suspend fun i(className: String, methodName: String, message: String) {
            addLog(AppLog(getTimeString(), message, Level.INFO, className, methodName))
        }

        suspend fun i(message: String) {
            i("", "", message)
        }

        suspend fun e(className: String, methodName: String, message: String) {
            addLog(AppLog(getTimeString(), message, Level.ERROR, className, methodName))
        }

        suspend fun e(message: String) {
            e("", "", message)
        }

        suspend fun inMessage(className: String, methodName: String, message: String) {
            addLog(AppLog(getTimeString(), message, Level.IN_MESSAGE, className, methodName))
        }

        suspend fun inMessage(message: String) {
            inMessage("", "", message)
        }

        suspend fun outMessage(className: String, methodName: String, message: String) {
            addLog(AppLog(getTimeString(), message, Level.OUT_MESSAGE, className, methodName))
        }

        suspend fun outMessage(message: String) {
            outMessage("", "", message)
        }

        private fun getTimeString(): String {
            return formatter.format(Date())
        }

        fun getLogs(): List<AppLog> {
            return logs
        }

        //Reactive part
        private const val BUFFER_LENGTH = 100

        private val _myLogs = MutableSharedFlow<List<AppLog>>(replay = 1, extraBufferCapacity = 64)

        init {
            val logs = getLogs()
            _myLogs.tryEmit(logs)
        }

        val logsFlow: SharedFlow<List<AppLog>> = _myLogs
        private var searchQuery = ""
        private var filterLevel = ""


        fun getFilteredLogs(): List<AppLog> {
            val filteredLogs = if (searchQuery.isNotEmpty())
                logs.filter { it.getUserMessage().toLowerCase().contains(searchQuery.toLowerCase()) }
                    .filter { it.level.getLevelPrefix().contains(filterLevel) }
            else
                logs.filter { it.level.getLevelPrefix().contains(filterLevel) }
            val from = Math.max(filteredLogs.size - BUFFER_LENGTH, 0)
            val to = filteredLogs.size

            return filteredLogs.subList(from, to)
        }

        private fun notifyListUpdates() {
            _myLogs.tryEmit(getFilteredLogs())
        }

        //SEARCH AND FILTER
        fun removeSearchQuery() {
            searchQuery = ""
            notifyListUpdates()
        }

        fun updateSearchQuery(query: String) {
            searchQuery = query
            notifyListUpdates()
        }

        fun filterLevelAll() {
            filterLevel = ""
            notifyListUpdates()
        }

        fun filterLevel(level: Level) {
            filterLevel = level.getLevelPrefix()
            notifyListUpdates()
        }

        fun setCurrentSession(session: Session) {
            this.session = session
        }
    }
}