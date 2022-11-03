package com.saifer.storyapp.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import com.saifer.storyapp.api.data.RegisterModel
import com.saifer.storyapp.api.ApiConfig
import com.saifer.storyapp.api.responses.RegisterResponse
import com.saifer.storyapp.databinding.ActivityRegisterBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private lateinit var btnRegister: Button

    lateinit var registerModel: RegisterModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        btnRegister = binding.btnRegister

        btnRegister.setOnClickListener {
            val name = binding.edRegisterName.text.toString()
            val email = binding.edRegisterEmail.text.toString()
            val passw = binding.edRegisterPassword.text.toString()
            registerModel = RegisterModel(name, email, passw)
            val client = ApiConfig.getApiService().register(registerModel)
            client.enqueue(object : Callback<RegisterResponse> {
                override fun onResponse(
                    call: Call<RegisterResponse>,
                    response: Response<RegisterResponse>
                ) {
                    if (response.isSuccessful) {
                        val responseBody = response.body()
                        if (responseBody != null) {
                            Toast.makeText(this@RegisterActivity, responseBody.message.toString(), Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        if (response.code() == 400){
                            Toast.makeText(this@RegisterActivity, "Error! cek kembali data anda", Toast.LENGTH_SHORT).show()
                        }
                        // 400 : Untuk nama yang kosong, atau email salah format, atau password yang kosong
                    }
                }
                override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                    Log.e("Error on Register Activity", "${t.message}")
                }
            })
        }
    }
}