package com.cosmetics.data.repository.home.categories

import com.cosmetics.data.networking.service.HomeService
import com.cosmetics.data.utils.safeApiCall
import com.cosmetics.domain.model.Results
import com.cosmetics.domain.model.home.request.HomeResponse
import com.cosmetics.domain.repository.CategoriesRepository

class CategoriesRepositoryImpl(private val homeService: HomeService) :
    CategoriesRepository {
    override suspend fun getCategories(): Results<HomeResponse> {
        return safeApiCall {
            homeService.getCategories()
        }
    }
}