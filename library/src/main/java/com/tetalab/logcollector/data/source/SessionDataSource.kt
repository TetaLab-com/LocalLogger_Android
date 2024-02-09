package com.tetalab.logcollector.data.source

import com.tetalab.logcollector.data.model.Session
import java.util.Collections

class SessionDataSource {

    companion object {
        private val sessions = Collections.synchronizedList(mutableListOf<Session>())

        fun init() {

        }

        private fun addSession(session: Session) {
            sessions.add(session)
        }

        fun getSessions(): List<Session> {
            return sessions
        }

        init {
            addSession(Session(0, 1, "12345"))
            addSession(Session(1, 2, "23456"))
            addSession(Session(2, 3, "34567"))
            addSession(Session(3, 4, "45678"))
        }
    }
}