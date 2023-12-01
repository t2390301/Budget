package com.example.budget.viewmodel

import com.example.budget.model.data.SmsData

sealed class AppState {
    data class Success(val data: List<SmsData>) : AppState()
    data class Error(val error: Throwable) : AppState()
    data object Loading : AppState()
}
