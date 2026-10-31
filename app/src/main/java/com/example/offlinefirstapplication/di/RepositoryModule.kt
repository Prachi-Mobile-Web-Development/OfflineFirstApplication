package com.example.offlinefirstapplication.di

import com.example.offlinefirstapplication.data.repositoryImpl.GetPostRepositoryImpl
import com.example.offlinefirstapplication.domain.repositroy.GetPostRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun provideRepositoryModule(getPostRepositoryImpl: GetPostRepositoryImpl): GetPostRepository
}