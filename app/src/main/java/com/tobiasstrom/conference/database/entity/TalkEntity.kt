package com.tobiasstrom.conference.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class TalkEntity(
    @PrimaryKey
    val id: String,
    @ColumnInfo(name = "is_favorite") var isFavorite: Boolean
)
