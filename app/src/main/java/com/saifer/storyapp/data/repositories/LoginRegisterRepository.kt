package com.saifer.storyapp.data.repositories

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.saifer.storyapp.data.remote.model.LoginModel
import com.saifer.storyapp.data.remote.model.RegisterModel
import com.saifer.storyapp.data.remote.responses.LoginResponse
import com.saifer.storyapp.data.remote.responses.RegisterResponse
import com.saifer.storyapp.data.remote.retrofit.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginRegisterRepository(private val apiService: ApiService) {

    private lateinit var loginModel: LoginModel
    private lateinit var registerModel: RegisterModel

    fun login(email: String, password: String) : LiveData<String?> {
        val token = MutableLiveData<String>(null)
        loginModel = LoginModel(email, password)
                val client = apiService.login(loginModel)
        client.enqueue(object : Callback<LoginResponse> {
            override fun onResponse(
                call: Call<LoginResponse>,
                response: Response<LoginResponse>
            ) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        token.value = responseBody.loginResult?.token.toString()
                    }
                }
            }
            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                Log.e("Error on Login Activity", "${t.message}")
            }
        })
        return token
    }

    fun register(name: String, email: String, password: String) : LiveData<Boolean> {
        val isSuccess = MutableLiveData(false)
        registerModel = RegisterModel(name, email, password)
        val client = apiService.register(registerModel)
        client.enqueue(object : Callback<RegisterResponse> {
            override fun onResponse(
                call: Call<RegisterResponse>,
                response: Response<RegisterResponse>
            ) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        isSuccess.value = true
                    }
                } else {
                    isSuccess.value = false
                }
            }
            override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                Log.e("Error on Register Activity", "${t.message}")
            }
        })
        return isSuccess
    }
}