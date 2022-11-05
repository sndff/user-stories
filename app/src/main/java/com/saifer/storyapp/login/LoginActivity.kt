package com.saifer.storyapp.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.saifer.storyapp.databinding.ActivityLoginBinding
import com.saifer.storyapp.session.SessionManager

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var viewModel: LoginRegisterViewModel
    private lateinit var session: SessionManager



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.supportActionBar?.hide()

        session = SessionManager(this@LoginActivity)

        binding = ActivityLoginBinding.inflate(layoutInflater)

        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[LoginRegisterViewModel::class.java]
        viewModel.playAnimationLogin(binding)

        binding.btnLogin.setOnClickListener {
            if (binding.edLoginEmail.text.toString() == ""){
                binding.edLoginEmail.error = "Input email"
            } else if (binding.edLoginPassword.text.toString() == "") {
                binding.edLoginPassword.error = "Input Password"
            } else {
                binding.progressBar.visibility = View.VISIBLE
                viewModel.login(this@LoginActivity, binding.edLoginEmail.text.toString(), binding.edLoginPassword.text.toString(), session, binding)
            }
        }

        binding.btnRegister.setOnClickListener{
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }
}