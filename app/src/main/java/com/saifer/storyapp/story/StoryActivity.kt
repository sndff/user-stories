package com.saifer.storyapp.story

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.saifer.storyapp.MainActivity
import com.saifer.storyapp.R
import com.saifer.storyapp.databinding.ActivityStoryBinding
import com.saifer.storyapp.session.SessionManager

class StoryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityStoryBinding
    private lateinit var rvStory: RecyclerView
    private lateinit var viewModel: StoryViewModel
    private lateinit var session: SessionManager


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[StoryViewModel::class.java]

        session  = SessionManager(this@StoryActivity)


        rvStory = binding.rvStory
        rvStory.setHasFixedSize(true)
        binding.progressBar.visibility = View.VISIBLE
        viewModel.getAllStories(this@StoryActivity, rvStory, session, binding)

        binding.fbNewStory.setOnClickListener{
            val i = Intent(this@StoryActivity, NewStoryActivity::class.java)
            startActivity(i)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.option_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){

            R.id.btn_refresh -> {
                binding.progressBar.visibility = View.VISIBLE
                viewModel.getAllStories(this@StoryActivity, rvStory, session, binding)
                return true
            }
            R.id.btn_logout -> {
                session.logout()
                val i = Intent(this@StoryActivity, MainActivity::class.java)
                finish()
                startActivity(i)
                Toast.makeText(this@StoryActivity, getString(R.string.logged_out), Toast.LENGTH_SHORT).show()
                return true
            }
            else -> {
                return true
            }
        }
    }
}