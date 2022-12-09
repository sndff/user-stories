package com.saifer.storyapp.ui.detail

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
class DetailStoryViewModelTest{
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainDispatcherRules = MainDispatcherRule()

    @Mock
    private lateinit var storyRepository: StoryRepository
    private lateinit var viewModel: DetailStoryViewModel
    private val sessionManager = SessionManager(Mockito.mock(Context::class.java))
    private val dummyId = "dummy_id"

    @Before
    fun setup(){
        viewModel = DetailStoryViewModel(storyRepository)
    }

    @Test
    fun `When Detail Story Loaded Successfully`(){
        viewModel.detail(dummyId, sessionManager)
        Mockito.verify(storyRepository).getDetailStory(dummyId, sessionManager)
    }
}