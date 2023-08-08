package com.saifer.storyapp.data.di

import com.saifer.storyapp.data.remote.retrofit.ApiConfig
import com.saifer.storyapp.data.repositories.StoryRepository

object StoryInjection {
    fun provideRepository(): StoryRepository{
        val apiService = ApiConfig.getApiService()
        return StoryRepository(apiService)
    }
}