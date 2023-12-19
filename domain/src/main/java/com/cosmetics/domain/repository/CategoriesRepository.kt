package com.cosmetics.domain.repository

import com.cosmetics.domain.model.Results
import com.cosmetics.domain.model.home.request.HomeResponse

interface CategoriesRepository {
    suspend fun getCategories(): Results<HomeResponse>
}