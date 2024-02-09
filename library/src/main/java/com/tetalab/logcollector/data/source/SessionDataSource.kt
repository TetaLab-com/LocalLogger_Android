package com.tetalab.logcollector.data.source

import com.tetalab.logcollector.data.model.Session
import com.tetalab.logcollector.data.room.AppLogDAO
import com.tetalab.logcollector.data.room.LogDatabase
import com.tetalab.logcollector.data.room.SessionDAO
import java.text.SimpleDateFormat
import java.util.Collections
import java.util.Date
import java.util.Locale
import java.util.TimeZone

class SessionDataSource {

    companion object {
        private val sessions = Collections.synchronizedList(mutableListOf<Session>())

        private lateinit var dao: SessionDAO
        private var activeSession: Session? = null

        fun init() {
            dao = LogDatabase.getInstance().sessionDao()
        }


        private suspend fun addSession(session: Session) {
            sessions.add(session)
        }

        suspend fun getSessions(): List<Session> {
            return sessions
        }

        suspend fun createNewSession() {
            val formatter = SimpleDateFormat("dd.yyyy.MM HH:mm:ss", Locale.getDefault())
            formatter.timeZone = TimeZone.getTimeZone("UTC")
            val date = formatter.format(Date())
            var session = Session(0, 0, date)

            dao.insertAll(session)

            activeSession = dao.getLastSession()
            LogsDataSource.setCurrentSession(activeSession!!)
        }

        fun getActiveSession(): Session? {
            return activeSession
        }
    }
}