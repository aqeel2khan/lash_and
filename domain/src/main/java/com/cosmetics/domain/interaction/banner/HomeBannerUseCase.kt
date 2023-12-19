package com.cosmetics.domain.interaction.banner

import com.cosmetics.domain.base.BaseUseCase
import com.cosmetics.domain.model.Results
import com.cosmetics.domain.model.home.banner.HomeBannerResponse

interface HomeBannerUseCase : BaseUseCase<String, HomeBannerResponse> {

    override suspend operator fun invoke(emptyString: String): Results<HomeBannerResponse>

}
