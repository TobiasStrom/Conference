package com.tobiasstrom.conference.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.tobiasstrom.conference.database.entity.TalkEntity
import com.tobiasstrom.conference.model.Talk

@Dao
interface TalkDao {
    @Query("SELECT * FROM talk_table")
    fun getTalks(): List<TalkEntity>

    @Query("SELECT * FROM talk_table WHERE is_favorite")
    fun getFavoriteTalks(): List<TalkEntity>

    @Query("SELECT * FROM talk_table WHERE id == :talkId")
    fun getFavoriteTalk(talkId: String): TalkEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addTalk(talk: TalkEntity)
}