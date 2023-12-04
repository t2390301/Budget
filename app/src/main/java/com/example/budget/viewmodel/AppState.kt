package com.example.budget.viewmodel

sealed class AppState<T> {
    data class Loading<T>(val isLoading: Boolean = true) : AppState<T>()
    data class Success<T>(val data: T?) : AppState<T>()
    data class Error<T>(val error: Throwable) : AppState<T>()
}
