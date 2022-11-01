package com.saifer.storyapp.story

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.saifer.storyapp.R
import com.saifer.storyapp.api.ApiConfig
import com.saifer.storyapp.api.data.StoryModel
import com.saifer.storyapp.api.responses.DetailStoryResponse
import com.saifer.storyapp.databinding.ActivityDetailStoryBinding
import com.saifer.storyapp.session.SessionManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailStoryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailStoryBinding
    private lateinit var viewModel: DetailStoryViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailStoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val session = SessionManager(this@DetailStoryActivity)
        viewModel = ViewModelProvider(this)[DetailStoryViewModel::class.java]

        val story = intent.getParcelableExtra<StoryModel>(EXTRA_USER) as StoryModel

        viewModel.getDetailStory(this@DetailStoryActivity, binding, story.id, session)

    }

    companion object {
        var EXTRA_USER = ""
    }
}