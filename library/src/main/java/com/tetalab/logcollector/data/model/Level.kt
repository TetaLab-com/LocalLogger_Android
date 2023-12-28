package com.tetalab.logcollector.data.model

import android.graphics.Color

enum class Level(val value: String, val color: Int) {
    INFO("Info", Color.argb(255, 182, 179, 87)),
    WARNING("Warning", Color.argb(255, 0, 0, 255)),
    ERROR("Error", Color.argb(255, 255, 0, 0)),
    IN_MESSAGE("IN", Color.argb(255, 182, 87, 169)),//used for messages received at Mobile phone
    OUT_MESSAGE("OUT", Color.argb(255, 100, 182, 87))//used for messages sent from Mobile phone
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
}