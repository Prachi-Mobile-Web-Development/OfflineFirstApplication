package com.example.offlinefirstapplication.di

import android.content.Context
import androidx.room.Room
import com.example.offlinefirstapplication.data.local.PostDao
import com.example.offlinefirstapplication.data.local.PostDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context)
            : PostDatabase =
        Room.databaseBuilder(context, PostDatabase::class.java, "post_database").build()

    @Provides
    @Singleton
    fun providePostDao(
        database: PostDatabase
    ): PostDao {
        return database.PostDao()
    }
}