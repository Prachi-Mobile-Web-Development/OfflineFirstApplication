package com.example.offlinefirstapplication.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.offlinefirstapplication.domain.model.Post
import com.example.offlinefirstapplication.domain.usecase.FetchPostUseCase
import com.example.offlinefirstapplication.domain.usecase.RefreshPostUseCase
import com.example.offlinefirstapplication.presentation.screen.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PostViewModel @Inject constructor(
    private val refreshPostUseCase: RefreshPostUseCase,
    private val fetchPostUseCase: FetchPostUseCase
) : ViewModel() {

    fun refreshPosts() {
        viewModelScope.launch {
            refreshPostUseCase()
        }
    }

    val postUiState: Flow<UiState<List<Post>>> =
        fetchPostUseCase().map { data ->
            if (data.isEmpty())
                UiState.Empty
            else
                UiState.Success(data)
        }.onStart {
            emit(UiState.Loading)
        }.catch { e ->
            emit(UiState.Error(e))
        }
            .stateIn(
                scope = viewModelScope,
                initialValue = UiState.Loading,
                started = SharingStarted.WhileSubscribed(5000)
            )

}