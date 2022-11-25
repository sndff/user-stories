package com.saifer.storyapp.data.repositories

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.saifer.storyapp.data.remote.responses.DetailStoryResponse
import com.saifer.storyapp.data.remote.responses.ListStoryItem
import com.saifer.storyapp.data.remote.responses.StoriesResponse
import com.saifer.storyapp.data.remote.responses.Story
import com.saifer.storyapp.data.remote.retrofit.ApiConfig
import com.saifer.storyapp.session.SessionManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class StoryRepository() {

    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    // stories
    private var _stories = MutableLiveData<List<ListStoryItem>>()
    val stories: LiveData<List<ListStoryItem>> = _stories

    //detail
    private var _detail = MutableLiveData<Story>()
    val detail: LiveData<Story> = _detail

    fun getAllStories(sessionManager: SessionManager){
        executorService.execute { getStories(sessionManager) }
    }

    fun getDetailStory(id: String, sessionManager: SessionManager){
        executorService.execute { detailStory(id, sessionManager) }
    }


    private fun getStories(sessionManager: SessionManager){
        val client = ApiConfig.getApiService().getStories("Bearer ${sessionManager.getToken()}")
        client.enqueue(object : Callback<StoriesResponse> {
            override fun onResponse(
                call: Call<StoriesResponse>,
                response: Response<StoriesResponse>
            ) {
                if (response.body() != null){
                    _stories.value = response.body()?.listStory
                }
            }
            override fun onFailure(call: Call<StoriesResponse>, t: Throwable) {
                Log.e("Error on Story Activity", "${t.message}")
            }
        })
    }

    fun getStoriesWithLocation(){

    }

    fun detailStory(id: String, sessionManager: SessionManager){
        val client = ApiConfig.getApiService().getDetailStory(id, "Bearer ${sessionManager.getToken()}")
        client.enqueue(object : Callback<DetailStoryResponse> {
            override fun onResponse(
                call: Call<DetailStoryResponse>,
                response: Response<DetailStoryResponse>
            ) {
                if (response.isSuccessful) {
                    if (response.body() != null){
                        _detail.value = response.body()?.story!!
                    }
                }
            }
            override fun onFailure(call: Call<DetailStoryResponse>, t: Throwable) {
                Log.e("Error on Story Activity", "${t.message}")
            }
        })
    }
}