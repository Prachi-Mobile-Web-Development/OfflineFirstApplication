package com.example.offlinefirstapplication.domain.usecase

import com.example.offlinefirstapplication.domain.repositroy.GetPostRepository
import javax.inject.Inject

class FetchPostUseCase @Inject constructor(private val getPostRepository: GetPostRepository) {
     operator fun invoke() =
        getPostRepository.observePosts()
}