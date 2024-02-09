package com.tetalab.logcollector.data.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.tetalab.logcollector.data.model.Session

@Dao
interface SessionDAO {
    @Query("SELECT * FROM Session")
    fun getAll(): List<Session>

    @Query("SELECT * FROM Session ORDER BY date_time DESC LIMIT 1")
    fun getLastSession() : Session

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg sessions: Session)

    @Update
    fun updateAll(vararg sessions: Session)

    @Delete
    fun delete(session: Session)

}