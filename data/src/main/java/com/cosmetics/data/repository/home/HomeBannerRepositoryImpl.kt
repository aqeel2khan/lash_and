package com.cosmetics.data.repository.home

import com.cosmetics.data.networking.service.HomeService
import com.cosmetics.data.utils.safeApiCall
import com.cosmetics.domain.repository.home.banner.HomeBannerRepository

class HomeBannerRepositoryImpl(private val homeService: HomeService) :
    HomeBannerRepository {
    override suspend fun getHomeBanner() =
        safeApiCall {
            homeService.getHomeBanner()
        }

}