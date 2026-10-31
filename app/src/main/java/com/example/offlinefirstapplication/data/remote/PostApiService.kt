package com.example.offlinefirstapplication.data.remote

import com.example.offlinefirstapplication.data.dto.PostDto
import retrofit2.http.GET

interface PostApiService {
    @GET("posts")
    suspend fun fetchPosts():List<PostDto>
}