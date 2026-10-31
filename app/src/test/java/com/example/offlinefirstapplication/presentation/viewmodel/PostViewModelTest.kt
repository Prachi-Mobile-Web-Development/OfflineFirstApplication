package com.example.offlinefirstapplication.presentation.viewmodel

import app.cash.turbine.test
import com.example.offlinefirstapplication.domain.model.Post
import com.example.offlinefirstapplication.domain.usecase.FetchPostUseCase
import com.example.offlinefirstapplication.domain.usecase.RefreshPostUseCase
import com.example.offlinefirstapplication.presentation.screen.UiState
import io.mockk.Runs
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.just
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import okhttp3.Dispatcher
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class PostViewModelTest {
    private val testDispatcher= StandardTestDispatcher()
    private val refreshPostUseCase: RefreshPostUseCase=mockk()
    private val fetchPostUseCase: FetchPostUseCase= mockk()
    private lateinit var postViewModel: PostViewModel
    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        clearAllMocks()
    }

    @Test
    fun `Given RefreshUseCase when refreshPost is called then verify RefreshUseCase is invoked`() {
        runTest {
            coEvery { refreshPostUseCase() } just Runs
            coEvery { fetchPostUseCase() } returns flowOf(emptyList())
            postViewModel= PostViewModel(refreshPostUseCase,fetchPostUseCase)
            postViewModel.refreshPosts()
            advanceUntilIdle()
            coVerify (exactly = 1) { refreshPostUseCase()  }

        }
    }

    @Test
    fun `Given FetchPostUseCase returns post When collecting postUiState then emit Success State`() {
        runTest {
            val post = listOf(Post(id=1, userId = 1, title = "title", body = "body"))
            coEvery { refreshPostUseCase() } just Runs
            coEvery { fetchPostUseCase() } returns flowOf(post)
            postViewModel= PostViewModel(refreshPostUseCase,fetchPostUseCase)
            postViewModel.postUiState.test {
                val loading = awaitItem()
                assertTrue(loading is UiState.Loading)
                val state = awaitItem()
                assertTrue(state is UiState.Success)
                assertEquals(post, (state as UiState.Success).data)
                coVerify(exactly = 1) { fetchPostUseCase() }
            }
        }
    }
}