package com.saifer.storyapp.ui.login

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.saifer.storyapp.R
import com.saifer.storyapp.databinding.ActivityLoginBinding
import com.saifer.storyapp.session.SessionManager
import com.saifer.storyapp.ui.list.StoryActivity

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var session: SessionManager

    private val viewModel: LoginRegisterViewModel by viewModels {
        LoginRegisterViewModelFactory()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.supportActionBar?.hide()

        session = SessionManager(this@LoginActivity)

        binding = ActivityLoginBinding.inflate(layoutInflater)

        setContentView(binding.root)

        playAnimation(binding)
        binding.btnLogin.setOnClickListener {
            if (binding.edLoginEmail.text.toString() == ""){
                binding.edLoginEmail.error = getString(R.string.login_error_input_email)
            } else if (binding.edLoginPassword.text.toString() == "") {
                binding.edLoginPassword.error = getString(R.string.login_error_input_password)
            } else {
                binding.progressBar.visibility = View.VISIBLE
                login(binding.edLoginEmail.text.toString(), binding.edLoginPassword.text.toString())
            }
        }

        binding.btnRegister.setOnClickListener{
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }

    private fun login(email: String, password: String) {
        viewModel.login(email, password).observe(this){
            if (it == null) {
                binding.progressBar.visibility = View.VISIBLE
            } else {
                session.setToken(it)
                session.setLogin(true)
                binding.progressBar.visibility = View.GONE
                val i = Intent(this, StoryActivity::class.java)
                startActivity(i)
                finish()
            }
        }
    }

    private fun playAnimation(binding: ActivityLoginBinding){
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

}