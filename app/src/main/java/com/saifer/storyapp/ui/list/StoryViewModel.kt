package com.saifer.storyapp.ui.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.saifer.storyapp.data.di.StoryInjection
import com.saifer.storyapp.data.repositories.StoryRepository

class StoryViewModel(private val repository: StoryRepository) : ViewModel(){
    fun story(token: String) = repository.getStoryPaging(token)
}

class StoryViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(StoryViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return StoryViewModel(StoryInjection.provideRepository()) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}