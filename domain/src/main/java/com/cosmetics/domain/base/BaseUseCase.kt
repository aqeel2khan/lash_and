package com.cosmetics.domain.base

import com.cosmetics.domain.model.Results

interface BaseUseCase<T : Any, R : Any> {
    suspend operator fun invoke(param: T): Results<R>
}