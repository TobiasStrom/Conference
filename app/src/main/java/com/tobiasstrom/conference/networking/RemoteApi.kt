package com.tobiasstrom.conference.networking

import com.google.gson.Gson
import com.tobiasstrom.conference.model.LikeResponse
import com.tobiasstrom.conference.model.Speaker
import com.tobiasstrom.conference.model.Talk
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


const val BASE_URL = "https://us-central1-conference-app-7be28.cloudfunctions.net/api/"

class RemoteApi(private val apiService: RemoteApiService) {

    private val gson = Gson()

    fun getTalk(talkId: String, onGetTask: (Talk?, Throwable?) -> Unit){
        apiService.getTask(talkId).enqueue(object : Callback<ResponseBody>{
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                val jsonBody = response.body()?.string()

                if(jsonBody == null){
                    onGetTask(null ,NullPointerException("No data available!") )
                }

                val data = gson.fromJson(jsonBody,Talk::class.java)

                if(data != null){
                    onGetTask(data, null)
                }else{
                    onGetTask(null, NullPointerException("No data available!"))
                }

            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                onGetTask(null, NullPointerException("No data available!"))
            }

        })
    }

    fun getTasks(onTasksReceived: (List<Talk>, Throwable?) -> Unit) {
        apiService.getTasks().enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                val jsonBody = response.body()?.string()

                if (jsonBody == null) {
                    onTasksReceived(emptyList(), NullPointerException("No data available!"))
                    return
                }

                val data = gson.fromJson(jsonBody, Array<Talk>::class.java).toList()

                if (data.isNotEmpty()) {
                    onTasksReceived(data, null)
                } else {
                    onTasksReceived(emptyList(), NullPointerException("No data available!"))
                }
            }
            override fun onFailure(call: Call<ResponseBody>, error: Throwable) {
                onTasksReceived(emptyList(), error)
            }
        })
    }

    fun getSpeaker(speakerId: String,onSpeakerReceived: (Speaker?, Throwable?) -> Unit){
        apiService.getSpeaker(speakerId).enqueue(object : Callback<ResponseBody>{
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                val jsonBody = response.body()?.string()

                if(jsonBody == null){
                    onSpeakerReceived(null, NullPointerException("No data available"))
                    return
                }
                val data = gson.fromJson(jsonBody, Speaker::class.java)
                if(data != null){
                    onSpeakerReceived(data, null)
                }else{
                    onSpeakerReceived(null, NullPointerException("No data available"))
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                onSpeakerReceived(null, t)
            }
        })
    }

    fun likeTalk(talkId: String){
        apiService.likeTalk(talkId).enqueue(object : Callback<ResponseBody>{
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) : Unit {
               val jsonBody = response.body()?.string()


            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable): Unit {

            }
        })
    }
}