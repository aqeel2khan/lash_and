package com.cosmetics.domain.repository.home.banner

import com.cosmetics.domain.model.Results
import com.cosmetics.domain.model.home.banner.HomeBannerResponse

interface HomeBannerRepository {
    suspend fun getHomeBanner(): Results<HomeBannerResponse>
}