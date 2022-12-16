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
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class StoryRepository(private val apiService: ApiService) {

    // stories
    private var _stories = MutableLiveData<List<ListStoryItem>>()
    val stories: LiveData<List<ListStoryItem>> = _stories

    // paging
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

    //detail
    private var _detail = MutableLiveData<Story>()
    val detail: LiveData<Story> = _detail

    fun getStoriesWithLocation(sessionManager: SessionManager) : Boolean? {
        var isSuccess: Boolean? = null
        val client = ApiConfig.getApiService().getStories("Bearer ${sessionManager.getToken()}", "1")
        client.enqueue(object : Callback<StoriesResponse> {
            override fun onResponse(
                call: Call<StoriesResponse>,
                response: Response<StoriesResponse>
            ) {
                if (response.body() != null){
                    _stories.value = response.body()?.listStory
                    isSuccess = true
                }
                else {
                    isSuccess = false
                }
            }
            override fun onFailure(call: Call<StoriesResponse>, t: Throwable) {
                Log.e("Error on Story Activity", "${t.message}")
            }
        })
        return isSuccess
    }

    fun detailStory(id: String, sessionManager: SessionManager) : Boolean?{
        var isSuccess: Boolean? = null
        val client = ApiConfig.getApiService().getDetailStory(id, "Bearer ${sessionManager.getToken()}")
        client.enqueue(object : Callback<DetailStoryResponse> {
            override fun onResponse(
                call: Call<DetailStoryResponse>,
                response: Response<DetailStoryResponse>
            ) {
                if (response.isSuccessful) {
                    if (response.body() != null){
                        _detail.value = response.body()?.story!!
                        isSuccess = true
                    }
                } else {
                    isSuccess = false
                }
            }
            override fun onFailure(call: Call<DetailStoryResponse>, t: Throwable) {
                Log.e("Error on Story Activity", "${t.message}")
            }
        })
        return isSuccess
    }
}