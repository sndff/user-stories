package com.saifer.storyapp.ui.post

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.saifer.storyapp.data.repositories.NewStoryRepository
import com.saifer.storyapp.ui.DataDummy
import com.saifer.storyapp.ui.utils.LiveDataTestUtil.getOrAwaitValue
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class NewStoryViewModelTest{
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var repository: NewStoryRepository

    private lateinit var viewModel: NewStoryViewModel

    private val dummyData = DataDummy.generateDummyPostStory()

    private val dummyToken = DataDummy.generateDummyToken()

    @Before
    fun setup(){
        viewModel = NewStoryViewModel(repository)
    }

    @Test
    fun `When Post Story Success Should Return True`(){
        val expectedResult = MutableLiveData<Boolean>()
        expectedResult.value = true

        Mockito.`when`(repository.postImage(dummyData, dummyToken)).thenReturn(expectedResult)

        val actualResult = viewModel.post(dummyData, dummyToken).getOrAwaitValue()

        Mockito.verify(repository).postImage(dummyData, dummyToken)

        assertTrue(actualResult)
    }

    @Test
    fun `When Post Story Success Should Return False`(){
        val expectedResult = MutableLiveData<Boolean>()
        expectedResult.value = false

        Mockito.`when`(repository.postImage(dummyData, dummyToken)).thenReturn(expectedResult)

        val actualResult = viewModel.post(dummyData, dummyToken).getOrAwaitValue()

        Mockito.verify(repository).postImage(dummyData, dummyToken)

        assertFalse(actualResult)
    }
}