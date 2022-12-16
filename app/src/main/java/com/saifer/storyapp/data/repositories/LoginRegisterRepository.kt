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
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class LoginRegisterRepository(private val apiService: ApiService) {

    private lateinit var loginModel: LoginModel
    private lateinit var registerModel: RegisterModel

    private var _token = MutableLiveData<String>(null)
    val token: LiveData<String> = _token

    private var _status = MutableLiveData<Boolean?>(null)
    val status: LiveData<Boolean?> = _status

    fun login(email: String, password: String) : Boolean? {
        var isSuccess: Boolean? = null
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
                        _token.value = responseBody.loginResult?.token.toString()
                        isSuccess = true
                    }
                } else {
                    isSuccess = false
                }
            }
            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                Log.e("Error on Login Activity", "${t.message}")
            }
        })
        return isSuccess
    }

    fun register(name: String, email: String, password: String) : Boolean? {
        var isSuccess: Boolean? = null
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
                        _status.value = responseBody.error!!
                        isSuccess = true
                    }
                } else {
                    _status.value = true
                    isSuccess = false
                }
            }
            override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                Log.e("Error on Register Activity", "${t.message}")
            }
        })
        return isSuccess
    }
}