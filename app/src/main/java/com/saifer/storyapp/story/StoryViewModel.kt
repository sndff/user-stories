package com.saifer.storyapp.story

import android.content.Intent
import android.content.res.Configuration
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityOptionsCompat
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.saifer.storyapp.adapter.ListStoryAdapter
import com.saifer.storyapp.api.ApiConfig
import com.saifer.storyapp.api.data.StoryModel
import com.saifer.storyapp.api.responses.StoriesResponse
import com.saifer.storyapp.databinding.ActivityStoryBinding
import com.saifer.storyapp.session.SessionManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class StoryViewModel : ViewModel(){
    fun showStory(activity: AppCompatActivity, rv: RecyclerView, data: ArrayList<StoryModel>) {
        if(activity.resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE){
            rv.layoutManager = GridLayoutManager(activity, 2)
        } else {
            rv.layoutManager = LinearLayoutManager(activity)
        }
        val listUserAdapter = ListStoryAdapter(data)
        rv.adapter = listUserAdapter

        listUserAdapter.setOnItemClickCallback(object : ListStoryAdapter.OnItemClickCallback {
            override fun onItemClicked(data: StoryModel) {
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

    fun showSelectedStory(activity: AppCompatActivity, story: StoryModel) {
        val intentDetailStoryActivity = Intent(activity, DetailStoryActivity::class.java)

        intentDetailStoryActivity.putExtra(DetailStoryActivity.EXTRA_USER, story)
        activity.startActivity(intentDetailStoryActivity, ActivityOptionsCompat.makeSceneTransitionAnimation(activity).toBundle())
    }

    fun getAllStories(activity: AppCompatActivity, rv: RecyclerView, sessionManager: SessionManager, binding: ActivityStoryBinding){
        val storyList = ArrayList<StoryModel>()
        val client = ApiConfig.getApiService().getStories("Bearer ${sessionManager.getToken()}")
        client.enqueue(object : Callback<StoriesResponse> {
            override fun onResponse(
                call: Call<StoriesResponse>,
                response: Response<StoriesResponse>
            ) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        for (i in 0 until responseBody.listStory!!.size){
                            val story = StoryModel(
                                responseBody.listStory[i]!!.photoUrl.toString(),
                                responseBody.listStory[i]!!.name.toString(),
                                responseBody.listStory[i]!!.description.toString(),
                                responseBody.listStory[i]!!.id.toString(),
                                responseBody.listStory[i]!!.createdAt.toString(),
                                responseBody.listStory[i]!!.lat.toString(),
                                responseBody.listStory[i]!!.lon.toString(),
                            )
                            storyList.add(story)
                        }
                        showStory(activity, rv, storyList)
                        binding.progressBar.visibility = View.GONE
                    }
                } else {
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(activity, "Stories not Found", Toast.LENGTH_SHORT).show()
                }
            }
            override fun onFailure(call: Call<StoriesResponse>, t: Throwable) {
                Log.e("Error on Story Activity", "${t.message}")
            }
        })
    }
}