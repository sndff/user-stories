package com.saifer.storyapp.ui.map

import androidx.lifecycle.ViewModel
import com.saifer.storyapp.data.repositories.StoryRepository
import com.saifer.storyapp.session.SessionManager

class MapViewModel : ViewModel() {
    val repository = StoryRepository()

    fun getStoryForMaps(sessionManager: SessionManager){
        repository.getStoryWithLocation(sessionManager)
    }
}