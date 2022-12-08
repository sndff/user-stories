package com.saifer.storyapp.ui.list

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.saifer.storyapp.MainActivity
import com.saifer.storyapp.R
import com.saifer.storyapp.data.paging.LoadingStateAdapter
import com.saifer.storyapp.databinding.ActivityStoryBinding
import com.saifer.storyapp.session.SessionManager
import com.saifer.storyapp.ui.map.MapsActivity
import com.saifer.storyapp.ui.post.NewStoryActivity

class StoryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityStoryBinding
    private lateinit var session: SessionManager

    private val viewModel: StoryViewModel by viewModels {
        StoryViewModelFactory()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        session  = SessionManager(this@StoryActivity)
        
        getStory()

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
                getStory()
                return true
            }
            R.id.btn_map ->{
                val i = Intent(this@StoryActivity, MapsActivity::class.java)
                startActivity(i)
                return true
            }
            R.id.btn_logout -> {
                session.logout()
                val i = Intent(this@StoryActivity, MainActivity::class.java)
                finish()
                startActivity(i)
                Toast.makeText(
                    this@StoryActivity,
                    getString(R.string.logged_out),
                    Toast.LENGTH_SHORT
                ).show()
                return true
            }
            else -> {
                return true
            }
        }
    }

    private fun getStory(){
        binding.progressBar.visibility = View.GONE
        val adapter = ListStoryAdapter()
        binding.rvStory.layoutManager = LinearLayoutManager(this)
        binding.rvStory.adapter = adapter.withLoadStateFooter(
            footer = LoadingStateAdapter{
                adapter.retry()
            }
        )
        viewModel.story(session.getToken()!!).observe(this){
            adapter.submitData(lifecycle, it)
        }
    }

}