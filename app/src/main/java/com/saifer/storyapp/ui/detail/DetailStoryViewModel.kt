package com.saifer.storyapp.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.saifer.storyapp.data.di.StoryInjection
import com.saifer.storyapp.data.repositories.StoryRepository
import com.saifer.storyapp.session.SessionManager

class DetailStoryViewModel(private val repository: StoryRepository) : ViewModel(){

    fun detail(id: String, token: String) = repository.detailStory(id, token)
}

class DetailStoryViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetailStoryViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return DetailStoryViewModel(StoryInjection.provideRepository()) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}