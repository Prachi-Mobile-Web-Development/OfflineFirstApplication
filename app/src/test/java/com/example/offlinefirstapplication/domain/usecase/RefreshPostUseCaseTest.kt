package com.example.offlinefirstapplication.domain.usecase

import com.example.offlinefirstapplication.domain.model.Post
import com.example.offlinefirstapplication.domain.repositroy.GetPostRepository
import io.mockk.Runs
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.just
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class RefreshPostUseCaseTest {
    private val testDispatcher = StandardTestDispatcher()
    private val getPostRepository: GetPostRepository = mockk()
    private lateinit var refreshPostUseCase: RefreshPostUseCase

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
    fun `Given GetRepository refresh post succeeds when invoking RefreshPostUseCase then verify Success`() {
        runTest {
            coEvery { getPostRepository.refreshPost() } just Runs
            refreshPostUseCase= RefreshPostUseCase(getPostRepository)
            getPostRepository.refreshPost()
            coVerify(exactly = 1) { getPostRepository.refreshPost() }

        }
    }
}