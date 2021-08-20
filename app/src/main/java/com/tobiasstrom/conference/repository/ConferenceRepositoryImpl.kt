package com.tobiasstrom.conference.repository

import com.tobiasstrom.conference.database.dao.TalkDao

class ConferenceRepositoryImpl(
    private val talkDao: TalkDao
): ConferenceRepository