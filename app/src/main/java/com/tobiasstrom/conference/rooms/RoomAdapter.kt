package com.tobiasstrom.conference.rooms

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.tobiasstrom.conference.R
import com.tobiasstrom.conference.databinding.ListItemRoomBinding
import com.tobiasstrom.conference.model.Room

class RoomAdapter(private val data: MutableList<Room>) : RecyclerView.Adapter<RoomAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: ListItemRoomBinding) : RecyclerView.ViewHolder(binding.root)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ListItemRoomBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
       with(holder){
           with(data[position]){
               binding.roomNameTextView.text = this.roomName
           }
       }
    }

    override fun getItemCount(): Int = data.size
}