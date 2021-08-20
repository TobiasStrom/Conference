package com.tobiasstrom.conference.model

import android.os.Parcelable
import com.squareup.moshi.Json
import java.util.*
import androidx.room.Ignore
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

//@Parcelize
class Talk(
    @field:Json(name = "room") val room: String,
    @field:Json(name = "title") val title: String,
    @Ignore
    @field:Json(name = "speakers") val speakers: List<String>?,
    @field:Json(name = "time") val time: Long,
    @PrimaryKey
    @field:Json(name = "id") val id: String,
    @field:Json(name = "likes") val likes: Int,
    @field:Json(name = "description") val description: String?,
    @field:Json(name = "topic") val topic: String?,
    @field:Json(name = "isFavorite") var isFavorite: Boolean = false
) /*: Parcelable */{


    fun getDateFromUnix(): Date {
        return Date(this.time * 1000)
    }

    var name: String = ""

}
