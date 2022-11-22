package com.saifer.storyapp.ui.detail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.saifer.storyapp.data.remote.model.StoryModel
import com.saifer.storyapp.databinding.ActivityDetailStoryBinding
import com.saifer.storyapp.session.SessionManager

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