package com.saifer.storyapp.login

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.saifer.storyapp.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private lateinit var btnRegister: Button
    private lateinit var viewModel: LoginRegisterViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        this.supportActionBar?.hide()

        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[LoginRegisterViewModel::class.java]
        viewModel.playAnimationRegister(binding)

        btnRegister = binding.btnRegister

        btnRegister.setOnClickListener {
            if (binding.edRegisterName.text.toString() == ""){
                binding.edRegisterName.error = "Name should not empty"
            } else if (binding.edRegisterEmail.text.toString() == ""){
                binding.edRegisterEmail.error = "Email should not empty"
            } else if (binding.edRegisterPassword.text.toString() == "") {
                binding.edRegisterPassword.error = "Password should not empty"
            } else {
                binding.progressBar.visibility = View.VISIBLE
                viewModel.register(this@RegisterActivity, binding.edRegisterName.text.toString(), binding.edRegisterEmail.text.toString(), binding.edRegisterPassword.text.toString(), binding)
            }
        }
    }
}