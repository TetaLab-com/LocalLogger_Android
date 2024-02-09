package com.tetalab.logcollector.data.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.tetalab.logcollector.data.model.AppLog

@Dao
interface AppLogDAO {
    @Query("SELECT * FROM AppLog")
    fun getAll(): List<AppLog>

    @Query("SELECT * FROM AppLog WHERE session_id IN (:sessionIds)")
    fun loadAllBySessionIds(sessionIds: IntArray): List<AppLog>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg logs: AppLog)

    @Update
    fun updateAll(vararg logs: AppLog)

    @Delete
    fun delete(log: AppLog)
}