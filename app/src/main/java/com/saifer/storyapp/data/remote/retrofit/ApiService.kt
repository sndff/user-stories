package com.saifer.storyapp.data.remote.retrofit

import com.saifer.storyapp.data.remote.model.LoginModel
import com.saifer.storyapp.data.remote.model.RegisterModel
import com.saifer.storyapp.data.remote.responses.*
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
        @Header("Authorization") header: String?,
        @Query("location") location: String? = null,
        @Query("page") page: String? = null,
        @Query("size") size: String? = null
    ): Call<StoriesResponse>

    @GET("stories")
    suspend fun getStoriesPaging(
        @Header("Authorization") header: String?,
        @Query("page") page: String? = null,
        @Query("size") size: String? = null
    ): StoriesResponse

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
        @Part("lat") lat: RequestBody?,
        @Part("lon") lon: RequestBody?,
        @Header("Authorization") header: String?
    ): Call<NewStoryResponse>
}