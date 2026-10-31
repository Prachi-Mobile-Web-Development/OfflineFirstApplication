package com.example.offlinefirstapplication.presentation.screen

import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.offlinefirstapplication.presentation.viewmodel.PostViewModel

@Composable
fun MainScreen(postViewModel: PostViewModel= hiltViewModel()){
     val uiState by postViewModel.postUiState.collectAsStateWithLifecycle(UiState.Loading)
    LaunchedEffect(Unit) {
     postViewModel.refreshPosts()
    }

    when(val post= uiState) {
      is  UiState.Empty -> {
            Text("No Data")
        }
        is UiState.Error -> {
            Text("${post.throwable.message}")

        }
       is UiState.Loading -> {
           CircularProgressIndicator()
        }
        is UiState.Success -> {
            PostScreen(post.data)
        }
    }

}