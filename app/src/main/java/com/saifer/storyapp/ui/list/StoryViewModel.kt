package com.saifer.storyapp.ui.list

import android.content.Intent
import android.content.res.Configuration
import android.view.View
import android.widget.AdapterView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityOptionsCompat
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.saifer.storyapp.data.remote.responses.ListStoryItem
import com.saifer.storyapp.data.repositories.StoryRepository
import com.saifer.storyapp.databinding.ActivityStoryBinding
import com.saifer.storyapp.session.SessionManager
import com.saifer.storyapp.ui.detail.DetailStoryActivity

class StoryViewModel : ViewModel(){

    private val repository = StoryRepository()

    fun story(activity: AppCompatActivity, sessionManager: SessionManager, binding: ActivityStoryBinding){
        var stories: List<ListStoryItem>
        repository.getAllStories(sessionManager)
        repository.stories.observeForever {
            stories = it
            showStory(activity, binding.rvStory, stories)
            binding.progressBar.visibility = View.GONE
        }
    }

    private fun showStory(activity: AppCompatActivity, rv: RecyclerView, data: List<ListStoryItem>) {
        if(activity.resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE){
            rv.layoutManager = GridLayoutManager(activity, 2)
        } else {
            rv.layoutManager = LinearLayoutManager(activity)
        }
        val listStoryAdapter = ListStoryAdapter(data)
        rv.adapter = listStoryAdapter

        listStoryAdapter.setOnItemClickCallback(object : ListStoryAdapter.OnItemClickCallback {
            override fun onItemClicked(data: ListStoryItem?) {
                showSelectedStory(activity, data)
            }

            override fun onItemClick(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                // do nothing
            }
        })
    }

    fun showSelectedStory(activity: AppCompatActivity, story: ListStoryItem?) {
        val intentDetailStoryActivity = Intent(activity, DetailStoryActivity::class.java)

        intentDetailStoryActivity.putExtra(DetailStoryActivity.EXTRA_USER, story!!.id)
        activity.startActivity(intentDetailStoryActivity, ActivityOptionsCompat.makeSceneTransitionAnimation(
            activity
        ).toBundle())
    }
}