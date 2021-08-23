package com.tobiasstrom.conference.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.tobiasstrom.conference.database.dao.TalkDao
import com.tobiasstrom.conference.database.entity.TalkEntity
import com.tobiasstrom.conference.model.Talk

const val DATABASE_VERSION = 1

@Database(
    entities = [TalkEntity::class],
    version = DATABASE_VERSION
)


abstract class ConferenceDatabase : RoomDatabase() {

    companion object {
        private const val DATABASE_NAME = "Conference"

        fun buildDatabase(context: Context): ConferenceDatabase{
            return Room.databaseBuilder(
                context,
                ConferenceDatabase::class.java,
                DATABASE_NAME
            )
                .allowMainThreadQueries()
                .build()
        }
    }

    abstract fun talkDao(): TalkDao

}