package com.saifer.storyapp.ui.detail

import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.saifer.storyapp.data.di.StoryInjection
import com.saifer.storyapp.data.repositories.StoryRepository
import com.saifer.storyapp.databinding.ActivityDetailStoryBinding
import com.saifer.storyapp.session.SessionManager

class DetailStoryViewModel(val repository: StoryRepository) : ViewModel(){

    fun detail(id: String, sessionManager: SessionManager){
        repository.getDetailStory(id, sessionManager)
    }
}

class DetailStoryViewModelFactory() : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetailStoryViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return DetailStoryViewModel(StoryInjection.provideRepository()) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}