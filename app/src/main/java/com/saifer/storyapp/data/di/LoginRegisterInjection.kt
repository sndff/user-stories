package com.saifer.storyapp.data.di

import com.saifer.storyapp.data.remote.retrofit.ApiConfig
import com.saifer.storyapp.data.repositories.LoginRegisterRepository

object LoginRegisterInjection {
    fun provideRepository(): LoginRegisterRepository{
        val apiService = ApiConfig.getApiService()
        return LoginRegisterRepository(apiService)
    }
}