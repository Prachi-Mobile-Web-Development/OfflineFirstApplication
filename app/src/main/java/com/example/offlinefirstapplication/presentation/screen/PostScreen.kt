package com.example.offlinefirstapplication.presentation.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.offlinefirstapplication.domain.model.Post

@Composable
fun PostScreen(posts:List<Post>){
    LazyColumn{
        items(items = posts, key = {it.id}){post->
            PostItem(post)
        }
    }

}

@Composable
fun PostItem(post: Post) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(text = post.title, style = MaterialTheme.typography.titleLarge)
        Text(text = post.body, style = MaterialTheme.typography.titleMedium)


    }
}