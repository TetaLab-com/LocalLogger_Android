package com.tetalab.logcollector.data.model

import android.graphics.Color

enum class Level(val value: String) {
    INFO("Info"),
    WARNING("Warning"),
    ERROR("Error"),
    IN_MESSAGE("IN"),//used for messages received at Mobile phone
    OUT_MESSAGE("OUT")//used for messages sent from Mobile phone
    ;

    fun getLevelPrefix(): String {
        return when(this) {
            INFO -> "I: "
            WARNING -> "D: "
            ERROR -> "E: "
            IN_MESSAGE -> "<--: "
            OUT_MESSAGE -> "-->: "
        }
    }

    fun getColor(): Int {
        return when(this) {
            INFO -> Color.argb(255, 182, 179, 87)
            WARNING -> Color.argb(255, 0, 0, 255)
            ERROR -> Color.argb(255, 255, 0, 0)
            IN_MESSAGE -> Color.argb(255, 182, 87, 169)
            OUT_MESSAGE -> Color.argb(255, 100, 182, 87)
        }
    }
}