package com.saifer.storyapp.ui.detail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.saifer.storyapp.data.remote.responses.Story
import com.saifer.storyapp.data.repositories.StoryRepository
import com.saifer.storyapp.session.SessionManager
import com.saifer.storyapp.ui.DataDummy
import com.saifer.storyapp.ui.utils.LiveDataTestUtil.getOrAwaitValue
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class DetailStoryViewModelTest{

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var repository: StoryRepository

    private lateinit var viewModel: DetailStoryViewModel

    private val dataDummy = DataDummy.generateDummyDetailStory()

    private val tokenDummy = DataDummy.generateDummyToken()

    @Before
    fun setup(){
        viewModel = DetailStoryViewModel(repository)
    }

    @Test
    fun `When Success Get Detail Story Should Not Null and Return True`() {
        val expectedDetail = MutableLiveData<Story>()
        expectedDetail.value = dataDummy

        `when`(repository.detailStory(dataDummy.id!!, tokenDummy)).thenReturn(expectedDetail)

        val actualDetail = viewModel.detail(dataDummy.id!!, tokenDummy).getOrAwaitValue()
        Mockito.verify(repository).detailStory(dataDummy.id!!, tokenDummy)
        Assert.assertNotNull(actualDetail)
        Assert.assertEquals(expectedDetail.value, actualDetail)
        Assert.assertTrue(actualDetail == expectedDetail.value)
    }

    @Test
    fun `When Failed Get Detail Story Should Null ane Return False`() {
        val expectedDetail = MutableLiveData<Story>()
        expectedDetail.value = null

        `when`(repository.detailStory(dataDummy.id!!, tokenDummy)).thenReturn(expectedDetail)

        val actualDetail = viewModel.detail(dataDummy.id!!, tokenDummy).getOrAwaitValue()
        Mockito.verify(repository).detailStory(dataDummy.id!!, tokenDummy)
        Assert.assertNull(actualDetail)
        Assert.assertTrue(actualDetail == expectedDetail.value)
    }
}