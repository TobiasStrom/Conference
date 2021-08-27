package com.tobiasstrom.conference.rooms

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.tobiasstrom.conference.databinding.FragmentRoomsBinding
import com.tobiasstrom.conference.databinding.ScheduleFragmentBinding
import com.tobiasstrom.conference.model.Room


class RoomsFragment : Fragment() {

    private val rooms: List<Room> = listOf(
        Room("Felix 1"),
        Room("Felix 2"),
        Room("Lancing")
    )

    private val adapter = RoomAdapter(rooms.toMutableList())

    companion object {
        fun newInstance() = RoomViewModel()
    }

    private lateinit var viewModel: RoomViewModel
    private var _binding: FragmentRoomsBinding? = null
    private val binding get() = _binding!!



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRoomsBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(RoomViewModel::class.java)
        // TODO: Use the ViewModel
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.roomRecyclerView.layoutManager = LinearLayoutManager(activity)
        binding.roomRecyclerView.adapter = adapter

    }
}