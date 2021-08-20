package com.tobiasstrom.conference.common.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.tobiasstrom.conference.R
import com.tobiasstrom.conference.databinding.ListItemTalkBinding
import com.tobiasstrom.conference.model.Talk
import com.tobiasstrom.conference.schedule.ScheduleFragmentDirections


class ScheduleAdapter : RecyclerView.Adapter<ScheduleAdapter.ViewHolder>() {

    private val data: MutableList<Talk> = mutableListOf()

    inner class ViewHolder(val binding: ListItemTalkBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ListItemTalkBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder){
            with(data[position]){
                Log.d("Talks", "onBindViewHolder ${this.title}")
                binding.talkNameTextView.text = this.title
                binding.talkRoomTextView.text = this.room
                binding.talkTimeTextView.text = this.getDateFromUnix().toString()
                binding.root.setOnClickListener {
                    val action = ScheduleFragmentDirections.actionTalk()
                    it.findNavController().navigate(action)
                }
            }
        }
    }

    override fun getItemCount(): Int = data.size

    fun setData(data: List<Talk>){
        this.data.clear()
        this.data.addAll(data)
        this.data.forEach {
            Log.d("Talks", "setData: ${it.title}")
        }

    }
}