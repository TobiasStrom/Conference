package com.tobiasstrom.conference.model

import com.squareup.moshi.Json

data class Speaker (
    @field:Json(name = "name") val name: String,
    @field:Json(name = "image") val image: String?,
    @field:Json(name = "bio") val bio: String?,
    @field:Json(name = "id") val id: String?)