package com.example.offlinefirstapplication.domain.repositroy

import com.example.offlinefirstapplication.domain.model.Post
import kotlinx.coroutines.flow.Flow

interface GetPostRepository {
    suspend fun refreshPost()
    fun observePosts(): Flow<List<Post>>
}