package com.android.movieapp.base

sealed class DataState {
    data class Error(val error: String) : DataState()
    object Loading : DataState()
    data class Success<T>(val data: T) : DataState()
}