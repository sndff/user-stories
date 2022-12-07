package com.saifer.storyapp.ui.detail

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.saifer.storyapp.databinding.ActivityDetailStoryBinding
import com.saifer.storyapp.session.SessionManager

class DetailStoryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailStoryBinding

    private val viewModel: DetailStoryViewModel by viewModels {
        DetailStoryViewModelFactory()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailStoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val session = SessionManager(this@DetailStoryActivity)

        val id = intent.getStringExtra(ID)

        viewModel.detail(id!!, session, binding, this)
    }

    companion object {
        var ID = ""
    }
}