package com.tetalab.logcollector.data.model

data class Log(
    val dateTime: String,
    val message: String,
    val level: Level = Level.WARNING,
    val className: String = "",
    val methodName: String = ""
) {

    fun getUserMessage(): String {
        return if (className.isNotEmpty()) {
            if (methodName.isNotEmpty())
                return "[${className}] [${methodName}] ${message}"
            else

                return "[${className}] ${message}"
        } else {
            if (methodName.isNotEmpty())
                return "[${methodName}] ${message}"
            else
                message
        }
    }

    override fun toString(): String {
        return "$dateTime ${level.getLevelPrefix()} ${getUserMessage()}"
    }

}