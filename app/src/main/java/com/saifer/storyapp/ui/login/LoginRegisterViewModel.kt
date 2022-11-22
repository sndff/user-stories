package com.saifer.storyapp.ui.login

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import com.saifer.storyapp.data.remote.retrofit.ApiConfig
import com.saifer.storyapp.data.remote.model.LoginModel
import com.saifer.storyapp.data.remote.model.RegisterModel
import com.saifer.storyapp.data.remote.responses.LoginResponse
import com.saifer.storyapp.data.remote.responses.RegisterResponse
import com.saifer.storyapp.databinding.ActivityLoginBinding
import com.saifer.storyapp.databinding.ActivityRegisterBinding
import com.saifer.storyapp.session.SessionManager
import com.saifer.storyapp.ui.list.StoryActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginRegisterViewModel: ViewModel() {
    private lateinit var loginModel: LoginModel
    private lateinit var registerModel: RegisterModel


    fun login(activity: AppCompatActivity, email: String, password: String, sessionManager: SessionManager, binding: ActivityLoginBinding){
        loginModel = LoginModel(email, password)
        val client = ApiConfig.getApiService().login(loginModel)
        client.enqueue(object : Callback<LoginResponse> {
            override fun onResponse(
                call: Call<LoginResponse>,
                response: Response<LoginResponse>
            ) {
                if (response.isSuccessful) {
                    binding.progressBar.visibility = View.GONE
                    val responseBody = response.body()
                    if (responseBody != null) {
                        sessionManager.setLogin(true)
                        sessionManager.setToken(responseBody.loginResult?.token.toString())
                        val i = Intent(activity, StoryActivity::class.java)
                        activity.startActivity(i)
                        activity.finish()
                    }
                } else {
                    binding.progressBar.visibility = View.GONE
                    if (response.code() == 401){
                        Toast.makeText(activity, "Account not Found", Toast.LENGTH_SHORT).show()
                    } else if(response.code() == 400) {
                        Toast.makeText(activity, "Email Format may wrong", Toast.LENGTH_SHORT).show()
                    }
                }
            }
            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                Log.e("Error on Login Activity", "${t.message}")
            }
        })
    }

    fun register(activity: AppCompatActivity, name: String, email: String, password: String, binding: ActivityRegisterBinding){
        registerModel = RegisterModel(name, email, password)
        val client = ApiConfig.getApiService().register(registerModel)
        client.enqueue(object : Callback<RegisterResponse> {
            override fun onResponse(
                call: Call<RegisterResponse>,
                response: Response<RegisterResponse>
            ) {
                if (response.isSuccessful) {
                    binding.progressBar.visibility = View.GONE
                    val responseBody = response.body()
                    if (responseBody != null) {
                        activity.finish()
                        Toast.makeText(activity, responseBody.message.toString(), Toast.LENGTH_SHORT).show()
                        val i = Intent(activity, LoginActivity::class.java)
                        activity.startActivity(i)
                    }
                } else {
                    binding.progressBar.visibility = View.GONE
                    if (response.code() == 400){
                        Toast.makeText(activity, "Error! Data format may wrong, please recheck your data", Toast.LENGTH_SHORT).show()
                    }
                }
            }
            override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                Log.e("Error on Register Activity", "${t.message}")
            }
        })
    }

    fun playAnimationLogin(binding: ActivityLoginBinding){
        ObjectAnimator.ofFloat(binding.logo, View.TRANSLATION_X, -30f, 30f).apply {
            duration = 6000
            repeatCount = ObjectAnimator.INFINITE
            repeatMode = ObjectAnimator.REVERSE
        }.start()
        val login = ObjectAnimator.ofFloat(binding.btnLogin, View.ALPHA, 1f).setDuration(500)
        val signup = ObjectAnimator.ofFloat(binding.btnRegister, View.ALPHA, 1f).setDuration(500)

        val together = AnimatorSet().apply {
            playTogether(login, signup)
        }

        AnimatorSet().apply {
            playSequentially(login, signup, together)
            start()
        }
    }

    fun playAnimationRegister(binding: ActivityRegisterBinding){
        ObjectAnimator.ofFloat(binding.logo, View.TRANSLATION_X, -30f, 30f).apply {
            duration = 6000
            repeatCount = ObjectAnimator.INFINITE
            repeatMode = ObjectAnimator.REVERSE
        }.start()
        val login = ObjectAnimator.ofFloat(binding.btnRegister, View.ALPHA, 1f).setDuration(500)

        val together = AnimatorSet().apply {
            playTogether(login)
        }

        AnimatorSet().apply {
            playSequentially(login, together)
            start()
        }
    }

}