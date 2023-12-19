package com.cosmetics.domain.interaction.banner

import com.cosmetics.domain.model.Results
import com.cosmetics.domain.model.home.banner.HomeBannerResponse
import com.cosmetics.domain.repository.home.banner.HomeBannerRepository

class HomeBannerUseCaseImpl(
    private val homeBannerRepository: HomeBannerRepository
) : HomeBannerUseCase {
    override suspend fun invoke(emptyString: String): Results<HomeBannerResponse> =
        homeBannerRepository.getHomeBanner()
}