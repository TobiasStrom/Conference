package com.tobiasstrom.conference.schedule.talk

import android.net.ConnectivityManager
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.tobiasstrom.conference.App
import com.tobiasstrom.conference.R
import com.tobiasstrom.conference.databinding.FragmentTalkBinding
import com.tobiasstrom.conference.model.Speaker


import com.tobiasstrom.conference.model.Talk
import com.tobiasstrom.conference.networking.NetworkStatusChecker
import com.tobiasstrom.conference.utils.invisible
import com.tobiasstrom.conference.utils.toast
import com.tobiasstrom.conference.utils.visible

class TalkFragment : Fragment() {
    private val remoteApi = App.remoteApi
    private val networkStatusChecker by lazy {
        NetworkStatusChecker(activity?.getSystemService(ConnectivityManager::class.java))
    }

    private var talkId: String? = null
    private lateinit var talk: Talk
    private var speakers: MutableList<Speaker>? = mutableListOf()

    companion object {
        fun newInstance() = TalkFragment()
    }

    //private lateinit var viewModel:
    private var _binding: FragmentTalkBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentTalkBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            val safeArgs = TalkFragmentArgs.fromBundle(it)
            talkId = safeArgs.id
        }
        talkId?.let { getTalk(it) }

    }

    private fun getTalk(talkId: String){
        binding.progress.visible()
        binding.likeButtonImageButton.invisible()
        networkStatusChecker.performIfConnectedToInternet {
            remoteApi.getTalk(talkId){talk, error ->
                if(talk != null){
                    this.talk = talk
                    getSpeakers(talk.speakers)
                    onBuildView()

                }else if (error!= null){
                    onGetTalkFailed()
                }
            }
        }
    }


    private fun getSpeakers(speakers: List<String>?) {
        networkStatusChecker.performIfConnectedToInternet {
            speakers?.forEach {
                remoteApi.getSpeaker(it){speaker, throwable ->
                    if (speaker != null){
                        this.speakers?.add(speaker)
                        Log.d("Speaker", "Lengde på liste ${speakers.size}")
                    }
                }
            }

        }

    }

    private fun onBuildView(){
        setupFavoriteButton()
        binding.titleTextView.text = talk.title
        binding.descriptionTextView.text = talk.description
        binding.topicTextView.text = talk.topic
        binding.roomTextView.text = talk.room
        binding.timeTextView.text = talk.getDateFromUnix().toString()
        binding.likesTextView.text = getString(R.string.likes, talk.likes)
        Log.d("Speaker", "Lengde på liste ${this.speakers?.size}")
        binding.progress.invisible()
        binding.likeButtonImageButton.visible()

    }

    private fun onGetTalkFailed(){
        activity?.toast("Failed to fetch talk!")
    }

    private fun setupFavoriteButton(){
        setupFavoriteButtonImage(this.talk)
        setupFavoriteButtonClickListener(this.talk)
    }

    private fun setupFavoriteButtonImage(talk: Talk){
        if(talk.isFavorite){
            binding.likeButtonImageButton.setImageDrawable(activity?.getDrawable(R.drawable.ic_baseline_favorite_24))
        }else{
            binding.likeButtonImageButton.setImageDrawable(activity?.getDrawable(R.drawable.ic_baseline_favorite_border_24))
        }
    }

    private fun setupFavoriteButtonClickListener(talk: Talk){
        binding.likeButtonImageButton.setOnClickListener{ _ ->
            if (talk.isFavorite){
                binding.likeButtonImageButton.setImageDrawable(activity?.getDrawable(R.drawable.ic_baseline_favorite_border_24))
                this.talk.isFavorite = false
            }else{
                binding.likeButtonImageButton.setImageDrawable(activity?.getDrawable(R.drawable.ic_baseline_favorite_24))
                this.talk.isFavorite = true
            }

        }
    }


}