package com.cosmetics.core.base

import com.cosmetics.domain.model.Results

inline fun <T : Any> Results<T>.onSuccess(action: (T) -> Unit): Results<T> {
    if (this is Results.Success) action(data)
    return this
}

inline fun <T : Any> Results<T>.onFailure(action: (String) -> Unit): Results<T> {
    if (this is Results.Error) action(message)
    return this
}