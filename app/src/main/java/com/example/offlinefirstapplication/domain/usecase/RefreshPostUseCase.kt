package com.example.offlinefirstapplication.domain.usecase

import com.example.offlinefirstapplication.domain.repositroy.GetPostRepository
import javax.inject.Inject

class RefreshPostUseCase @Inject constructor(private val getPostRepository: GetPostRepository) {

    suspend operator fun invoke() = getPostRepository.refreshPost()
}