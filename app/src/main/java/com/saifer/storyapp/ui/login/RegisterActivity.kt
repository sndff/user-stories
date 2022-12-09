package com.saifer.storyapp.ui.login

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.saifer.storyapp.R
import com.saifer.storyapp.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding

    private val viewModel: LoginRegisterViewModel by viewModels {
        LoginRegisterViewModelFactory()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        this.supportActionBar?.hide()

        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        playAnimation(binding)
        binding.btnRegister.setOnClickListener {
            if (binding.edRegisterName.text.toString() == ""){
                binding.edRegisterName.error = getString(R.string.register_error_email)
            } else if (binding.edRegisterEmail.text.toString() == ""){
                binding.edRegisterEmail.error = getString(R.string.register_error_name)
            } else if (binding.edRegisterPassword.text.toString() == "") {
                binding.edRegisterPassword.error = getString(R.string.register_error_password)
            } else {
                register(
                    binding.edRegisterName.text.toString(),
                    binding.edRegisterEmail.text.toString(),
                    binding.edRegisterPassword.text.toString()
                )
                binding.progressBar.visibility = View.GONE
            }
        }
    }

    private fun register(name: String, email: String, password: String) {
        binding.progressBar.visibility = View.VISIBLE
        viewModel.register(name, email, password)
        viewModel.repository.status.observe(this){
            when (it) {
                false -> {
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(this, "Account Created", Toast.LENGTH_SHORT).show()
                    finish()
                }
                true -> {
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(this, "Error: Incorrect input format or email is already taken", Toast.LENGTH_SHORT).show()
                }
                null -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun playAnimation(binding: ActivityRegisterBinding){
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