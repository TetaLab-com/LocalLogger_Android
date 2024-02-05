package com.tetalab.logcollector.data.source

import com.tetalab.logcollector.data.model.Level
import com.tetalab.logcollector.data.model.AppLog
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
        private val logs = Collections.synchronizedList(mutableListOf<AppLog>())
        private var formatter: SimpleDateFormat = SimpleDateFormat("HH:mm:ssSSSS")

        private fun addLog(log: AppLog) {
            logs.add(log)
            android.util.Log.d("AppLog", "[${log.className}] [${log.methodName}] ${log.message}")
            notifyListUpdates()
        }

        fun w(className: String, methodName: String, message: String) {
            addLog(AppLog(getTimeString(), message, Level.WARNING, className, methodName))
        }

        fun w(message: String) {
            w("", "", message)
        }

        fun i(className: String, methodName: String, message: String) {
            addLog(AppLog(getTimeString(), message, Level.INFO, className, methodName))
        }

        fun i(message: String) {
            i("", "", message)
        }

        fun e(className: String, methodName: String, message: String) {
            addLog(AppLog(getTimeString(), message, Level.ERROR, className, methodName))
        }

        fun e(message: String) {
            e("", "", message)
        }

        fun inMessage(className: String, methodName: String, message: String) {
            addLog(AppLog(getTimeString(), message, Level.IN_MESSAGE, className, methodName))
        }

        fun inMessage(message: String) {
            inMessage("", "", message)
        }

        fun outMessage(className: String, methodName: String, message: String) {
            addLog(AppLog(getTimeString(), message, Level.OUT_MESSAGE, className, methodName))
        }

        fun outMessage(message: String) {
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
    }
}