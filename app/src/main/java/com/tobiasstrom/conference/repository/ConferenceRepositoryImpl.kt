package com.tobiasstrom.conference.repository

import com.tobiasstrom.conference.database.dao.TalkDao
import com.tobiasstrom.conference.database.entity.TalkEntity

class ConferenceRepositoryImpl(
    private val talkDao: TalkDao
): ConferenceRepository{
    override fun addFavorite(talkEntity: TalkEntity) = talkDao.addTalk(talkEntity)
    override fun getFavorite(talkId: String): TalkEntity? = talkDao.getFavoriteTalk(talkId)
    override fun getFavorites(): List<TalkEntity> = talkDao.getFavoriteTalks().filter { it.isFavorite }

}