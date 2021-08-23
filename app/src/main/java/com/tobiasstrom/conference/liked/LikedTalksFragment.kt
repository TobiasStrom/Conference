package com.tobiasstrom.conference.liked

import android.net.ConnectivityManager
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.tobiasstrom.conference.App
import com.tobiasstrom.conference.R
import com.tobiasstrom.conference.common.adapter.ScheduleAdapter
import com.tobiasstrom.conference.database.entity.TalkEntity
import com.tobiasstrom.conference.databinding.FragmentLikedTalksBinding
import com.tobiasstrom.conference.databinding.ScheduleFragmentBinding
import com.tobiasstrom.conference.model.Talk
import com.tobiasstrom.conference.networking.NetworkStatusChecker
import com.tobiasstrom.conference.schedule.ScheduleFragment
import com.tobiasstrom.conference.schedule.ScheduleViewModel
import com.tobiasstrom.conference.utils.toast
import kotlin.time.measureTime

class LikedTalksFragment : Fragment() {

    private var adapter = ScheduleAdapter()
    private val remoteApi = App.remoteApi
    private val networkStatusChecker by lazy {
        NetworkStatusChecker(activity?.getSystemService(ConnectivityManager::class.java))
    }
    private val repository by lazy {  App.repository }

    companion object {
        fun newInstance() = ScheduleFragment()
    }
    private lateinit var viewModel: LikedTalksViewModel
    private var _binding: FragmentLikedTalksBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentLikedTalksBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(LikedTalksViewModel::class.java)
        // TODO: Use the ViewModel
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.likedTaskRecyclerView.layoutManager = LinearLayoutManager(activity)
        binding.likedTaskRecyclerView.adapter = adapter
        initUi()

    }

    private fun initUi(){
        getLikedTalks()
    }

    private fun getLikedTalks(){
        networkStatusChecker.performIfConnectedToInternet {
            remoteApi.getTasks {talks, error ->
                if (talks.isNotEmpty()) {
                    onTalksReceived(talks)
                } else if (error != null) {
                    onGetTalksFailed()
                    Log.d("Talks", "It failed")
                }
            }
        }
    }

    private fun onTalksReceived(talks: List<Talk>) {
        val favoriteTask= mutableListOf<Talk>()
        val favorite = repository.getFavorites()
        talks.forEach {
            if(favorite.contains(TalkEntity(it.id, true))){
                favoriteTask.add(it)
            }
        }
        adapter.setData(favoriteTask)
    }

    private fun onGetTalksFailed(){
        activity?.toast("Failed to fetch talks!")
    }


}
