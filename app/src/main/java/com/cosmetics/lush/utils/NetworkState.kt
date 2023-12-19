package com.cosmetics.lush.utils

sealed class PagingState {
    object InitialLoading : PagingState()
    object Loading : PagingState()
    object Completed : PagingState()
    data class Failed(var errorMsg: String) : PagingState()
}