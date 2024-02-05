package com.tetalab.logcollector.data.model

data class AppLog(
    val dateTime: String,
    val message: String,
    val level: Level = Level.WARNING,
    val className: String = "",
    val methodName: String = ""
) {

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