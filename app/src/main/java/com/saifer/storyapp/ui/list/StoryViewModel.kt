package com.saifer.storyapp.ui.list

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.view.View
import android.widget.AdapterView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityOptionsCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.saifer.storyapp.data.di.Injection
import com.saifer.storyapp.data.remote.responses.ListStoryItem
import com.saifer.storyapp.data.repositories.StoryRepository
import com.saifer.storyapp.databinding.ActivityStoryBinding
import com.saifer.storyapp.session.SessionManager
import com.saifer.storyapp.ui.detail.DetailStoryActivity

class StoryViewModel(private val repository: StoryRepository) : ViewModel(){
    fun story(token: String) = repository.getStoryPaging(token).cachedIn(viewModelScope)
}

class StoryViewModelFactory() : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(StoryViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return StoryViewModel(Injection.provideRepository()) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}