package com.example.offlinefirstapplication.domain.usecase

import com.example.offlinefirstapplication.domain.model.Post
import com.example.offlinefirstapplication.domain.repositroy.GetPostRepository
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class FetchPostUseCaseTest {
    private val testDispatcher = StandardTestDispatcher()
    private val getPostRepository: GetPostRepository = mockk()
    private lateinit var fetchPostUseCase: FetchPostUseCase

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
    fun `Given getPostRepository response  succeeds when invoking fetchPostUseCase then verify success`() {
        runTest {
            val post = listOf(
                Post(
                    userId = 1,
                    id = 1,
                    title = "sunt aut facere repellat provident occaecati excepturi optio reprehenderit",
                    body = "quia et suscipit\nsuscipit recusandae consequuntur expedita et cum\nreprehenderit molestiae ut ut quas totam\nnostrum rerum est autem sunt rem eveniet architecto"
                )
            )
            every { getPostRepository.observePosts() } returns flowOf(post)
            fetchPostUseCase= FetchPostUseCase(getPostRepository)
            val result = getPostRepository.observePosts().first()

            assertEquals(post,result)
            verify (exactly = 1) { getPostRepository.observePosts() }
        }


    }
}