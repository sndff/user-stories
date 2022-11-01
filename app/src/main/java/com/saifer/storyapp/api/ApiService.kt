package com.saifer.storyapp.api

import com.saifer.storyapp.api.data.LoginModel
import com.saifer.storyapp.api.data.RegisterModel
import com.saifer.storyapp.api.responses.DetailStoryResponse
import com.saifer.storyapp.api.responses.LoginResponse
import com.saifer.storyapp.api.responses.RegisterResponse
import com.saifer.storyapp.api.responses.StoriesResponse
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @POST("register")
    fun register(
        @Body registerModel: RegisterModel
    ): Call<RegisterResponse>

    @POST("login")
    fun login(
        @Body loginModel: LoginModel
    ): Call<LoginResponse>

    @GET("stories")
    fun getStories(
        @Header("Authorization") header: String?
    ): Call<StoriesResponse>

    @GET("stories/{id}")
    fun getDetailStory(
        @Path("id") id: String,
        @Header("Authorization") header: String?
    ): Call<DetailStoryResponse>
}