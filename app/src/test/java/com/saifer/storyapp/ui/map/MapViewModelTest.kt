package com.saifer.storyapp.ui.map

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.saifer.storyapp.data.remote.responses.ListStoryItem
import com.saifer.storyapp.data.repositories.StoryRepository
import com.saifer.storyapp.ui.DataDummy
import com.saifer.storyapp.ui.utils.LiveDataTestUtil.getOrAwaitValue
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class MapViewModelTest{
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var repository: StoryRepository

    private lateinit var viewModel: MapViewModel

    private val dataDummy = DataDummy.generateDummyDataMap()

    private val tokenDummy = DataDummy.generateDummyToken()

    @Before
    fun setup(){
        viewModel = MapViewModel(repository)
    }

    @Test
    fun `When Success Get Map Data Should not Null and Return True`(){
        val expectedData = MutableLiveData<List<ListStoryItem>>()
        expectedData.value = dataDummy

        `when`(repository.getStoriesWithLocation(tokenDummy)).thenReturn(expectedData)

        val actualData = viewModel.getStoryForMaps(tokenDummy).getOrAwaitValue()
        Mockito.verify(repository).getStoriesWithLocation(tokenDummy)
        assertNotNull(actualData)
        assertEquals(expectedData.value, actualData)
        assertTrue(actualData == expectedData.value)
    }

    @Test
    fun `When Failed Get Map Data Should Null and Return False`(){
        val expectedData = MutableLiveData<List<ListStoryItem>>()
        expectedData.value = null

        `when`(repository.getStoriesWithLocation(tokenDummy)).thenReturn(expectedData)

        val actualData = viewModel.getStoryForMaps(tokenDummy).getOrAwaitValue()
        Mockito.verify(repository).getStoriesWithLocation(tokenDummy)
        assertNull(actualData)
        assertEquals(expectedData.value, actualData)
        assertFalse(actualData != expectedData.value)
    }
}