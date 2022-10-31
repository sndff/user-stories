package com.saifer.storyapp.api

import com.saifer.storyapp.api.data.LoginModel
import com.saifer.storyapp.api.data.RegisterModel
import com.saifer.storyapp.api.responses.LoginResponse
import com.saifer.storyapp.api.responses.RegisterResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @POST("register")
    fun register(
        @Body registerModel: RegisterModel
    ): Call<RegisterResponse>

    @POST("login")
    fun login(
        @Body loginModel: LoginModel
    ): Call<LoginResponse>
}