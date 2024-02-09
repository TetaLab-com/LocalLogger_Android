package com.tetalab.logcollector.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class AppLog(
    @PrimaryKey(autoGenerate = true) val uid: Int = 0,
    @ColumnInfo(name = "session_id") val sessionId: Int,
    @ColumnInfo(name = "date_time") val dateTime: String,
    @ColumnInfo(name = "message") val message: String,
    @ColumnInfo(name = "level") val level: Level = Level.WARNING,
    @ColumnInfo(name = "class_name") val className: String = "",
    @ColumnInfo(name = "method_name") val methodName: String = ""
) {
    constructor(
        sessionId: Int,
        dateTime: String,
        message: String,
        level: Level,
        className: String,
        methodName: String
    ) : this(0, sessionId, dateTime, message, level, className, methodName)

    constructor(
        dateTime: String,
        message: String,
        level: Level,
        className: String,
        methodName: String
    ) : this(0, 0, dateTime, message, level, className, methodName)

    fun getUserMessage(): String {
        return if (className.isNotEmpty()) {
            if (methodName.isNotEmpty())
                "[${className}] [${methodName}] $message"
            else
                "[${className}] $message"
        } else {
            if (methodName.isNotEmpty())
                "[${methodName}] $message"
            else
                message
        }
    }

    override fun toString(): String {
        return "$dateTime ${level.getLevelPrefix()} ${getUserMessage()}"
    }

}