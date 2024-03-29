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

    fun getStoriesWithLocation(token: String) : LiveData<List<ListStoryItem>?> {
        val stories = MutableLiveData<List<ListStoryItem>?>()
        val client = ApiConfig.getApiService().getStories("Bearer $token", "1")
        client.enqueue(object : Callback<StoriesResponse> {
            override fun onResponse(
                call: Call<StoriesResponse>,
                response: Response<StoriesResponse>
            ) {
                if (response.isSuccessful){
                    if (response.body() != null){
                        stories.value = response.body()?.listStory
                    }
                } else {
                    stories.value = null
                }
            }
            override fun onFailure(call: Call<StoriesResponse>, t: Throwable) {
                Log.e("Error on Story Activity", "${t.message}")
            }
        })
        return stories
    }

    fun detailStory(id: String, token: String) : LiveData<Story?> {
        val story = MutableLiveData<Story?>()
        val client = ApiConfig.getApiService().getDetailStory(id, "Bearer $token")
        client.enqueue(object : Callback<DetailStoryResponse> {
            override fun onResponse(
                call: Call<DetailStoryResponse>,
                response: Response<DetailStoryResponse>
            ) {
                if (response.isSuccessful) {
                    if (response.body() != null){
                        story.value = response.body()?.story!!
                    }
                } else {
                    story.value = null
                }
            }
            override fun onFailure(call: Call<DetailStoryResponse>, t: Throwable) {
                Log.e("Error on Story Activity", "${t.message}")
            }
        })
        return story
    }
}