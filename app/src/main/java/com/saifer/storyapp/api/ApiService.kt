package com.saifer.storyapp.api

import com.saifer.storyapp.api.data.LoginModel
import com.saifer.storyapp.api.data.RegisterModel
import com.saifer.storyapp.api.responses.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
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

    @Multipart
    @POST("stories")
    fun uploadImage(
        @Part file: MultipartBody.Part,
        @Part("description") description: RequestBody,
        @Header("Authorization") header: String?
    ): Call<NewStoryResponse>
}