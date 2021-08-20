package com.tobiasstrom.conference.schedule

import android.net.ConnectivityManager
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.tobiasstrom.conference.App
import com.tobiasstrom.conference.common.adapter.ScheduleAdapter

import com.tobiasstrom.conference.databinding.ScheduleFragmentBinding
import com.tobiasstrom.conference.model.Talk
import com.tobiasstrom.conference.networking.NetworkStatusChecker
import com.tobiasstrom.conference.utils.gone
import com.tobiasstrom.conference.utils.toast
import com.tobiasstrom.conference.utils.visible


class ScheduleFragment : Fragment() {

    private var adapter = ScheduleAdapter()
    private val remoteApi = App.remoteApi
    private val networkStatusChecker by lazy {
        NetworkStatusChecker(activity?.getSystemService(ConnectivityManager::class.java))
    }

    companion object {
        fun newInstance() = ScheduleFragment()
    }

    private lateinit var viewModel: ScheduleViewModel
    private var _binding: ScheduleFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = ScheduleFragmentBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ScheduleViewModel::class.java)
        // TODO: Use the ViewModel
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.scheduleRecyclerView.layoutManager = LinearLayoutManager(activity)

        binding.scheduleRecyclerView.adapter = adapter
        initUi()
    }
    private fun initUi(){
        binding.progress.visible()
        getAllTalks()
    }

    private fun getAllTalks(){
        binding.progress.visible()
        networkStatusChecker.performIfConnectedToInternet {
            remoteApi.getTasks {talks, error ->
                    if (talks.isNotEmpty()) {
                        onTalksReceived(talks)
                        Log.d("Talks", "getAllTask $talks")
                    } else if (error != null) {
                        onGetTalksFailed()
                        Log.d("Talks", "It failed")
                    }

            }
        }
    }

    override fun onResume() {
        super.onResume()
        binding.progress.gone()
    }

    private fun onTalksReceived(talks: List<Talk>) {
        binding.progress.gone()
        adapter.setData(talks)
    }

    private fun onGetTalksFailed(){
        activity?.toast("Failed to fetch talks!")
    }

}