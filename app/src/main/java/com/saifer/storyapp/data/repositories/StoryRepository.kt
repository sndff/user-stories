package com.saifer.storyapp.data.repositories

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.saifer.storyapp.data.paging.StoryPagingSource
import com.saifer.storyapp.data.remote.responses.DetailStoryResponse
import com.saifer.storyapp.data.remote.responses.ListStoryItem
import com.saifer.storyapp.data.remote.responses.StoriesResponse
import com.saifer.storyapp.data.remote.responses.Story
import com.saifer.storyapp.data.remote.retrofit.ApiConfig
import com.saifer.storyapp.data.remote.retrofit.ApiService
import com.saifer.storyapp.session.SessionManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class StoryRepository(private val apiService: ApiService) {

    fun getStoryPaging(token: String): LiveData<PagingData<ListStoryItem>> {
        return Pager(
            config = PagingConfig(
                pageSize = 5
            ),
            pagingSourceFactory = {
                StoryPagingSource(apiService, token)
            }
        ).liveData
    }

    fun getStoriesWithLocation(sessionManager: SessionManager) : LiveData<List<ListStoryItem>> {
        val stories = MutableLiveData<List<ListStoryItem>>()
        val client = ApiConfig.getApiService().getStories("Bearer ${sessionManager.getToken()}", "1")
        client.enqueue(object : Callback<StoriesResponse> {
            override fun onResponse(
                call: Call<StoriesResponse>,
                response: Response<StoriesResponse>
            ) {
                if (response.body() != null){
                    stories.value = response.body()?.listStory
                }
            }
            override fun onFailure(call: Call<StoriesResponse>, t: Throwable) {
                Log.e("Error on Story Activity", "${t.message}")
            }
        })
        return stories
    }

    fun detailStory(id: String, sessionManager: SessionManager) : LiveData<Story> {
        val story = MutableLiveData<Story>()
        val client = ApiConfig.getApiService().getDetailStory(id, "Bearer ${sessionManager.getToken()}")
        client.enqueue(object : Callback<DetailStoryResponse> {
            override fun onResponse(
                call: Call<DetailStoryResponse>,
                response: Response<DetailStoryResponse>
            ) {
                if (response.isSuccessful) {
                    if (response.body() != null){
                        story.value = response.body()?.story!!
                    }
                }
            }
            override fun onFailure(call: Call<DetailStoryResponse>, t: Throwable) {
                Log.e("Error on Story Activity", "${t.message}")
            }
        })
        return story
    }
}