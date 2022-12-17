package com.saifer.storyapp.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.saifer.storyapp.data.di.LoginRegisterInjection
import com.saifer.storyapp.data.repositories.LoginRegisterRepository

class LoginRegisterViewModel(private val repository: LoginRegisterRepository): ViewModel() {

    fun login(email: String, password: String) = repository.login(email, password)


    fun register(name: String, email: String, password: String) = repository.register(name, email, password)
}

class LoginRegisterViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginRegisterViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return LoginRegisterViewModel(LoginRegisterInjection.provideRepository()) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}