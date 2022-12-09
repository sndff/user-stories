package com.saifer.storyapp.ui.detail

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.saifer.storyapp.databinding.ActivityDetailStoryBinding
import com.saifer.storyapp.session.SessionManager

class DetailStoryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailStoryBinding
    private lateinit var session: SessionManager

    private val viewModel: DetailStoryViewModel by viewModels {
        DetailStoryViewModelFactory()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailStoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        session = SessionManager(this@DetailStoryActivity)

        val id = intent.getStringExtra(ID)

        getData(id!!)

    }

    private fun getData(id: String){
        viewModel.detail(id, session)
        viewModel.repository.detail.observe(this) {
            Glide.with(this)
                .load(it.photoUrl)
                .apply( RequestOptions().override(1000,1000))
                .into(binding.ivDetailPhoto)
            binding.tvDetailName.text = it.name
            binding.tvDetailDescription.text = it.description
            binding.progressBar.visibility = View.GONE
        }
    }

    companion object {
        var ID = ""
    }
}