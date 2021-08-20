package com.tobiasstrom.conference

import android.app.Application
import com.tobiasstrom.conference.database.ConferenceDatabase
import com.tobiasstrom.conference.networking.RemoteApi
import com.tobiasstrom.conference.networking.buildApiService

class App: Application() {
    companion object{
        private lateinit var instance: App

        private val apiService by lazy{
            buildApiService()
        }
        val remoteApi by lazy {
            RemoteApi(apiService)
        }

        private val database: ConferenceDatabase by lazy {
            ConferenceDatabase.buildDatabase(instance)
        }

    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }
}