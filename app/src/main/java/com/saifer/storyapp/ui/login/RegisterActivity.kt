package com.saifer.storyapp.ui.login

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.saifer.storyapp.R
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
                binding.edRegisterName.error = getString(R.string.register_error_email)
            } else if (binding.edRegisterEmail.text.toString() == ""){
                binding.edRegisterEmail.error = getString(R.string.register_error_name)
            } else if (binding.edRegisterPassword.text.toString() == "") {
                binding.edRegisterPassword.error = getString(R.string.register_error_password)
            } else {
                binding.progressBar.visibility = View.VISIBLE
                viewModel.register(this@RegisterActivity, binding.edRegisterName.text.toString(), binding.edRegisterEmail.text.toString(), binding.edRegisterPassword.text.toString(), binding)
            }
        }
    }
}