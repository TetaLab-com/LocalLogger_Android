package com.tetalab.logcollector.data.source

import com.tetalab.logcollector.data.model.Session
import java.util.Collections

class SessionDataSource {

    companion object {
        private val sessions = Collections.synchronizedList(mutableListOf<Session>())

        private fun addSession(session: Session) {
            sessions.add(session)
        }

        fun getSessions(): List<Session> {
            return sessions
        }

        init {
            addSession(Session("12345"))
            addSession(Session("23456"))
            addSession(Session("34567"))
            addSession(Session("45678"))
        }
    }
}