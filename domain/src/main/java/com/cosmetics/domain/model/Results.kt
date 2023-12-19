package com.cosmetics.domain.model


sealed class Results<out T : Any> {
    data class Success<T : Any>(val data: T) : Results<T>()
    data class Error(val message: String) : Results<Nothing>()
    data class Loading(val str: String = "") : Results<Nothing>()
}