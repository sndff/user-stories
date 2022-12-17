package com.saifer.storyapp.ui.list

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.AsyncPagingDataDiffer
import androidx.paging.PagingData
import androidx.paging.PagingSource
import androidx.paging.PagingState
import androidx.recyclerview.widget.ListUpdateCallback
import com.saifer.storyapp.data.remote.responses.ListStoryItem
import com.saifer.storyapp.data.repositories.StoryRepository
import com.saifer.storyapp.ui.DataDummy
import com.saifer.storyapp.ui.MainDispatcherRule
import com.saifer.storyapp.ui.utils.LiveDataTestUtil.getOrAwaitValue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class StoryViewModelTest{

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainDispatcherRules = MainDispatcherRule()

    @Mock
    private lateinit var repository: StoryRepository

    private lateinit var viewModel: StoryViewModel

    private val dataDummy = DataDummy.generateDummyStory()

    private val tokenDummy = DataDummy.generateDummyToken()

    @Before
    fun setup(){
        viewModel = StoryViewModel(repository)
    }

    @Test
    fun `When List Story Loaded Successfully`() = runTest {
        val data: PagingData<ListStoryItem> = StoryPagingSource.snapshot(dataDummy)
        val expectedData = MutableLiveData<PagingData<ListStoryItem>>()
        expectedData.value = data

        Mockito.`when`(repository.getStoryPaging(tokenDummy)).thenReturn(expectedData)

        val actualData: PagingData<ListStoryItem> = viewModel.story(tokenDummy).getOrAwaitValue()
        val differ = AsyncPagingDataDiffer(
            diffCallback = ListStoryAdapter.DIFF_CALLBACK,
            updateCallback = noopListUpdateCallback,
            workerDispatcher = Dispatchers.Main,
        )
        differ.submitData(actualData)

        Assert.assertNotNull(differ.snapshot())
        assertEquals(dataDummy, differ.snapshot())
        assertEquals(dataDummy.size, differ.snapshot().size)
        assertEquals(dataDummy[0].id, differ.snapshot()[0]?.id)
    }
}

class StoryPagingSource : PagingSource<Int, LiveData<List<ListStoryItem>>>() {
    companion object {
        fun snapshot(items: List<ListStoryItem>): PagingData<ListStoryItem> {
            return PagingData.from(items)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, LiveData<List<ListStoryItem>>>): Int {
        return 0
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, LiveData<List<ListStoryItem>>> {
        return LoadResult.Page(emptyList(), 0, 1)
    }
}

val noopListUpdateCallback = object : ListUpdateCallback {
    override fun onInserted(position: Int, count: Int) {}
    override fun onRemoved(position: Int, count: Int) {}
    override fun onMoved(fromPosition: Int, toPosition: Int) {}
    override fun onChanged(position: Int, count: Int, payload: Any?) {}
}