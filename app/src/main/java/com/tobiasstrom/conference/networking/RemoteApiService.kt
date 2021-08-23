package com.tobiasstrom.conference.networking

import com.tobiasstrom.conference.model.Speaker
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

interface RemoteApiService {
    @GET("talk")
    fun getTasks(): Call<ResponseBody>

    @GET("talk/{id}")
    fun getTask(@Path("id") talk: String): Call<ResponseBody>

    @POST("talk/{id}/like")
    fun likeTalk(@Path("id") talk: String): Call<ResponseBody>

    @GET("speaker/{id}")
    fun getSpeaker(@Path("id") speakerId: String): Call<ResponseBody>

}