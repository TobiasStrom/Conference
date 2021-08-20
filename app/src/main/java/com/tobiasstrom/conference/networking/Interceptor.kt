package com.tobiasstrom.conference.networking

import android.util.Log
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class Interceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request: Request = chain.request()
        Log.d("Talks","Request URL " + request.url)

        val response = chain.proceed(request)
        Log.d("Talks" , "Status code ${response.code}")

        //Comment out to run
         //response.body?.string()?.let { Log.d("Talks", "Body: $it") }

        return response
    }
}