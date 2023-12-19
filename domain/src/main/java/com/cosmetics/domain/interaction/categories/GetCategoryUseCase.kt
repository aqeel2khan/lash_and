package com.cosmetics.domain.interaction.categories

import com.cosmetics.domain.base.BaseUseCase
import com.cosmetics.domain.model.Results
import com.cosmetics.domain.model.home.request.HomeResponse

interface GetCategoryUseCase : BaseUseCase<String, HomeResponse> {

    override suspend operator fun invoke(emptyString: String): Results<HomeResponse>

}
