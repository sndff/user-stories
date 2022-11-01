package com.saifer.storyapp.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import com.saifer.storyapp.MainActivity
import com.saifer.storyapp.api.data.LoginModel
import com.saifer.storyapp.api.ApiConfig
import com.saifer.storyapp.api.responses.LoginResponse
import com.saifer.storyapp.api.responses.StoriesResponse
import com.saifer.storyapp.databinding.ActivityLoginBinding
import com.saifer.storyapp.session.SessionManager
import com.saifer.storyapp.story.StoryActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var btnLogin: Button
    private lateinit var btnRegister: Button
    private lateinit var loginModel: LoginModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val session = SessionManager(this@LoginActivity)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        btnLogin = binding.btnLogin
        btnRegister = binding.btnRegister

        btnLogin.setOnClickListener {
            val email = binding.edLoginEmail.text.toString()
            val passw = binding.edLoginPassword.text.toString()
            loginModel = LoginModel(email, passw)
            val client = ApiConfig.getApiService().login(loginModel)
            client.enqueue(object : Callback<LoginResponse> {
                override fun onResponse(
                    call: Call<LoginResponse>,
                    response: Response<LoginResponse>
                ) {
                    if (response.isSuccessful) {
                        val responseBody = response.body()
                        if (responseBody != null) {
                            session.setLogin(true)
                            session.setToken(responseBody.loginResult?.token.toString())
                            val i = Intent(this@LoginActivity, StoryActivity::class.java)
                            startActivity(i)
                            finish()
                        }
                    } else {
                        if (response.code() == 401){
                            Toast.makeText(this@LoginActivity, "Akun tidak ditemukan", Toast.LENGTH_SHORT).show()
                        } else if(response.code() == 400) {
                            Toast.makeText(this@LoginActivity, "Format Email Salah", Toast.LENGTH_SHORT).show()
                        }
                        // 401 : akun tidak ditemukan
                        // 400 : format email tidak valid
                    }
                }
                override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                    Log.e("Error on Login Activity", "${t.message}")
                }
            })
        }

        btnRegister.setOnClickListener{
            Toast.makeText(this@LoginActivity, "Register", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }


    }
}