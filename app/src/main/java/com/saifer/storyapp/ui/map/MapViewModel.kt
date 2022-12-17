package com.saifer.storyapp.ui.map

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.saifer.storyapp.data.di.StoryInjection
import com.saifer.storyapp.data.repositories.StoryRepository
import com.saifer.storyapp.session.SessionManager

class MapViewModel(private val repository: StoryRepository) : ViewModel() {

    fun getStoryForMaps(sessionManager: SessionManager) = repository.getStoriesWithLocation(sessionManager)

}

class MapViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MapViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MapViewModel(StoryInjection.provideRepository()) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}