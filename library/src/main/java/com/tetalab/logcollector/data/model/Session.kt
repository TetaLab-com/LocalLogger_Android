package com.tetalab.logcollector.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Session(
    @PrimaryKey(autoGenerate = true) val uid: Int,
    @ColumnInfo(name = "logs_count") val logsCount: Int,
    @ColumnInfo(name = "date_time") val dateTime: String
) {
}