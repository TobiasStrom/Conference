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
import com.tobiasstrom.conference.database.entity.TalkEntity
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
    private val repository by lazy {  App.repository }

    private var talkId: String? = null
    private lateinit var talk: Talk
    private lateinit var speakers: List<Speaker>

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
                    Log.d("Speaker", "Lengde på talk from this ${talk.speakers?.size}")
                    getSpeakers(talk.speakers)


                }else if (error!= null){
                    onGetTalkFailed()
                }
            }
        }
    }


    private fun getSpeakers(speakers: List<String>?) {
        var list: MutableList<Speaker> = mutableListOf()
        networkStatusChecker.performIfConnectedToInternet {
            speakers?.forEach {
                remoteApi.getSpeaker(it){speaker, throwable ->
                    if (speaker != null){
                        list.add(speaker)
                        Log.d("Speaker", "Kjør på ${speaker.name}")

                    }
                }
                Log.d("Speaker", "Kjør på")
            }
            Log.d("Speaker", "Lengde på speaker liste før ${speakers?.size}")
            Log.d("Speaker", "Lengde på list liste før ${list.size}")

            this.speakers = list
            Log.d("Speaker", "Lengde på list liste ${list.size}")
            Log.d("Speaker", "Lengde på this.speaker liste ${this.speakers.size}")
            onBuildView()

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
        if(this.speakers.isNotEmpty()){
            Log.d("Speakerw", "Lengde på liste ${this.speakers.get(0).name}")
        }else{
            Log.d("Speakerw", "Listen er tom")
        }

        binding.progress.invisible()
        binding.likeButtonImageButton.visible()

        repository.addFavorite(TalkEntity("c9bcc8c0-9dd1-5e7c-ac90-824c397b161f", false))

    }

    private fun getFavorites(): List<Talk>{
        return emptyList()
    }

    private fun onGetTalkFailed(){
        activity?.toast("Failed to fetch talk!")
    }

    private fun setupFavoriteButton(){
        setupFavoriteButtonImage(this.talk)
        setupFavoriteButtonClickListener(this.talk)
    }

    private fun setupFavoriteButtonImage(talk: Talk){
        repository.getFavorite(talk.id).let {
            if(it?.isFavorite == true){
                talk.isFavorite = true
                binding.likeButtonImageButton.setImageDrawable(activity?.getDrawable(R.drawable.ic_baseline_favorite_24))
            }else{
                binding.likeButtonImageButton.setImageDrawable(activity?.getDrawable(R.drawable.ic_baseline_favorite_border_24))
            }
        }

    }

    private fun setupFavoriteButtonClickListener(talk: Talk) {
        binding.likeButtonImageButton.setOnClickListener { _ ->
            if (talk.isFavorite) {
                repository.addFavorite(TalkEntity(talk.id, false))
                binding.likeButtonImageButton.setImageDrawable(activity?.getDrawable(R.drawable.ic_baseline_favorite_border_24))
                this.talk.isFavorite = false
            } else {
                remoteApi.likeTalk(talk.id)
                talk.likes++
                binding.likesTextView.text = getString(R.string.likes, talk.likes)
                repository.addFavorite(TalkEntity(talk.id, true))
                binding.likeButtonImageButton.setImageDrawable(activity?.getDrawable(R.drawable.ic_baseline_favorite_24))
                this.talk.isFavorite = true
            }
        }
    }


}