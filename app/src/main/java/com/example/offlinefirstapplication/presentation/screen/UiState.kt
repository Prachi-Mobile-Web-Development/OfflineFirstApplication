package com.example.offlinefirstapplication.presentation.screen

sealed class UiState <out T>{
    object Loading: UiState<Nothing>()
    object Empty: UiState<Nothing>()
    class Success<T>(val data:T): UiState<T>()
    class Error<T>(val throwable: Throwable): UiState<T>()
}