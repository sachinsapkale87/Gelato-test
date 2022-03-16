package com.sachin.test.home


import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import com.sachin.test.network.RetrofitService
import com.sachin.test.utils.FakeResponseUtils
import com.sachin.test.utils.MainCoroutineRule
import com.sachin.test.utils.runBlockingTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
class HomeViewModelTest {
    // Executes tasks in the Architecture Components in the same thread
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    // Set the main coroutines dispatcher for unit testing.
    @get:Rule
    var coroutineRule = MainCoroutineRule()

    @Mock
    private lateinit var retrofitService: RetrofitService

    @Mock
    private lateinit var fakeRepository: HomeRepository

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
    }

    @Test
    fun `test if home is loaded with images`() {
        coroutineRule.runBlockingTest {
            // Stubbing network calls with fake episode list
            whenever(retrofitService.getImageList(1,10))
                .thenReturn(FakeResponseUtils.getImageList())
//            // Stub repository with  list
            whenever(fakeRepository.getImageList(1,10))
                .thenReturn(FakeResponseUtils.getImageList())

            val homeViewModel = createHomeViewModel()
            homeViewModel.getImageListUsingStaeFlow()

            // Observe on `first` terminal operator for homeViewStateFlow
            val homeState = homeViewModel.pageUiStateFlow.first() as HomeViewModel.PageAPiuiState.Success
            assertThat(homeState).isNotNull()
            val userlist = homeState.list
            assertThat(userlist.isNotEmpty())
            // compare the response with fake list
            assertThat(userlist).hasSize(FakeResponseUtils.getImageList().body()!!.size)
            // compare the data and also order
            assertThat(userlist).containsExactlyElementsIn(
                FakeResponseUtils.getImageList().body()!!
            ).inOrder()
        }
    }

    private fun createHomeViewModel(): HomeViewModel {
        return HomeViewModel(fakeRepository)
    }

    @After
    fun release() {
        Mockito.framework().clearInlineMocks()
    }
}