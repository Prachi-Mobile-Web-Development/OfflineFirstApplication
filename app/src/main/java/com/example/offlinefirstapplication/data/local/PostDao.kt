package com.example.offlinefirstapplication.data.local

import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.REPLACE
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow

interface PostDao {
    @Query("select * from post order by id ASC")
    fun observePosts(): Flow<List<PostEntity>>

    @Insert(onConflict = REPLACE)
    fun insertPost(post: List<PostEntity>)

    @Query("Delete from post")
    suspend fun deleteAll()

    @Transaction
    suspend fun replacePost(post: List<PostEntity>){
        deleteAll()
        insertPost(post)
    }
}