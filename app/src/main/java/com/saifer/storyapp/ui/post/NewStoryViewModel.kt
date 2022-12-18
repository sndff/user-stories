package com.saifer.storyapp.ui.post

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.saifer.storyapp.data.di.NewStoryInjection
import com.saifer.storyapp.data.remote.model.PostModel
import com.saifer.storyapp.data.repositories.NewStoryRepository
import com.saifer.storyapp.session.SessionManager
import okhttp3.RequestBody
import java.io.File

class NewStoryViewModel(private val repository: NewStoryRepository) : ViewModel() {
    fun post(model: PostModel, token: String) = repository.postImage(model, token)
}

class NewStoryViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NewStoryViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return NewStoryViewModel(NewStoryInjection.provideRepository()) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}