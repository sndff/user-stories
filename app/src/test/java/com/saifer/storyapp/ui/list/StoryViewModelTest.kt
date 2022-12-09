package com.saifer.storyapp.ui.list

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.saifer.storyapp.data.repositories.StoryRepository
import com.saifer.storyapp.ui.DataDummy
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
class StoryViewModelTest{
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainDispatcherRules = MainDispatcherRule()

    @Mock
    private lateinit var storyRepository: StoryRepository

    private lateinit var viewModel: StoryViewModel

    private val dummyToken = DataDummy.generateDummyToken()

    @Before
    fun setup(){
        viewModel = StoryViewModel(storyRepository)
    }

    @Test
    fun `When Story List Loaded Successfully`(){
        viewModel.story(dummyToken)
        Mockito.verify(storyRepository).getStoryPaging(dummyToken)
    }
}