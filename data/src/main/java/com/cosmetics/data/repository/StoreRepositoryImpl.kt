package com.cosmetics.data.repository

import com.cosmetics.data.networking.service.HomeService
import com.cosmetics.data.utils.safeApiCall
import com.cosmetics.domain.model.Results
import com.cosmetics.domain.model.home.store.StoreListResponse
import com.cosmetics.domain.repository.home.StoreRepository

class StoreRepositoryImpl(private val homeService: HomeService) : StoreRepository {
    override suspend fun getStoreList(): Results<StoreListResponse> = safeApiCall {
        homeService.getStoreList()
    }
}