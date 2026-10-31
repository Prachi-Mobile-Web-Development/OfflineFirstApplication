package com.example.offlinefirstapplication.data.repositoryImpl

import com.example.offlinefirstapplication.data.dto.PostDto
import com.example.offlinefirstapplication.data.local.PostDao
import com.example.offlinefirstapplication.data.local.PostEntity
import com.example.offlinefirstapplication.data.mapper.toDomain
import com.example.offlinefirstapplication.data.mapper.toEntity
import com.example.offlinefirstapplication.data.remote.PostApiService
import io.mockk.Runs
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.just
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
class GetPostRepositoryImplTest {
    private val testDispatcher = StandardTestDispatcher()
    private val postApiService: PostApiService = mockk()
    private val postDao: PostDao = mockk()
    private lateinit var getPostRepositoryImpl: GetPostRepositoryImpl

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
    fun `Given fetchPost api response when refreshPost is called then save post`() {
        runTest {
            val postDto = listOf(
                PostDto(
                    userId = 1,
                    id = 1,
                    title = "sunt aut facere repellat provident occaecati excepturi optio reprehenderit",
                    body = "quia et suscipit\nsuscipit recusandae consequuntur expedita et cum\nreprehenderit molestiae ut ut quas totam\nnostrum rerum est autem sunt rem eveniet architecto"
                )
            )
            coEvery { postApiService.fetchPosts() } returns postDto
            val postEntity = postDto.map { it.toEntity() }
            coEvery { postDao.replacePost(postEntity) } just Runs
            getPostRepositoryImpl = GetPostRepositoryImpl(postApiService, postDao)
            getPostRepositoryImpl.refreshPost()
            coVerify(exactly = 1) { postApiService.fetchPosts() }
            coVerify(exactly = 1) { postDao.replacePost(postEntity) }
        }
    }

    @Test
    fun `Given PostDao ObservePost response when observe Post is called then return domain post`() {
        runTest {
            val postEntity = listOf(
                PostEntity(
                    userId = 1,
                    id = 1,
                    title = "sunt aut facere repellat provident occaecati excepturi optio reprehenderit",
                    body = "quia et suscipit\nsuscipit recusandae consequuntur expedita et cum\nreprehenderit molestiae ut ut quas totam\nnostrum rerum est autem sunt rem eveniet architecto"
                )
            )
            every { postDao.observePosts() } returns flowOf(postEntity)
            val post= postEntity.map { it.toDomain() }
            getPostRepositoryImpl = GetPostRepositoryImpl(postApiService, postDao)
            val result = getPostRepositoryImpl.observePosts().first()
            assertEquals(post,result)
            verify(exactly = 1) { postDao.observePosts()}
        }
    }
}