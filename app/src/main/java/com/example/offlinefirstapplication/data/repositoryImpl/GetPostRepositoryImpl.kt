package com.example.offlinefirstapplication.data.repositoryImpl

import com.example.offlinefirstapplication.data.local.PostDao
import com.example.offlinefirstapplication.data.mapper.toDomain
import com.example.offlinefirstapplication.data.mapper.toEntity
import com.example.offlinefirstapplication.data.remote.PostApiService
import com.example.offlinefirstapplication.domain.model.Post
import com.example.offlinefirstapplication.domain.repositroy.GetPostRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetPostRepositoryImpl @Inject constructor(
    private val postApiService: PostApiService,
    private val postDao: PostDao
) : GetPostRepository {
    override suspend fun refreshPost() {
        val response = postApiService.fetchPosts()
        postDao.replacePost(response.map { it.toEntity() })
    }

    override fun observePosts(): Flow<List<Post>> =
        postDao.observePosts().map { entities ->
            entities.map { it.toDomain() }
        }

}