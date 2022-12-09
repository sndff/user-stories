package com.saifer.storyapp.ui.map

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.saifer.storyapp.data.repositories.StoryRepository
import com.saifer.storyapp.session.SessionManager
import com.saifer.storyapp.ui.MainDispatcherRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class MapViewModelTest{

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainDispatcherRules = MainDispatcherRule()

    @Mock
    private lateinit var repository: StoryRepository

    private lateinit var viewModel: MapViewModel

    private val sessionManager = SessionManager(Mockito.mock(Context::class.java))

    @Before
    fun setup(){
        viewModel = MapViewModel(repository)
    }

    @Test
    fun `When Story With Location Loaded Successfully`(){
        viewModel.getStoryForMaps(sessionManager)
        Mockito.verify(repository).getStoryWithLocation(sessionManager)
    }
}