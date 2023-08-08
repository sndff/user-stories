package com.saifer.storyapp.data.di

import com.saifer.storyapp.data.remote.retrofit.ApiConfig
import com.saifer.storyapp.data.repositories.NewStoryRepository

object NewStoryInjection {
    fun provideRepository(): NewStoryRepository{
        val apiService = ApiConfig.getApiService()
        return NewStoryRepository(apiService)
    }
}