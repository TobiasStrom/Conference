package com.tobiasstrom.conference.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.tobiasstrom.conference.model.Talk

@Dao
interface TalkDao {
    @Query("SELECT * FROM TalkEntity")
    fun getTalks(): List<Talk>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addBook(talk: Talk)
}