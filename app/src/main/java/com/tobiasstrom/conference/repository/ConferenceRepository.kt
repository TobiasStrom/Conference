package com.tobiasstrom.conference.repository

import com.tobiasstrom.conference.database.entity.TalkEntity

interface ConferenceRepository{
    fun addFavorite(talkEntity: TalkEntity)

    fun getFavorite(talkId: String): TalkEntity?

    fun getFavorites(): List<TalkEntity>
}