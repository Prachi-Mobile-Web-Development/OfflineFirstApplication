package com.example.offlinefirstapplication.di

import com.example.offlinefirstapplication.data.remote.PostApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    private const val BASE_URL = "https://jsonplaceholder.typicode.com/"

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit =
        Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create())
            .client(
                OkHttpClient()
            ).build()

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): PostApiService =
        retrofit.create(PostApiService::class.java)
}