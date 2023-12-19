package com.cosmetics.domain.interaction.appInfo

import com.cosmetics.domain.base.BaseUseCase
import com.cosmetics.domain.model.Results
import com.cosmetics.domain.model.home.appinfo.AppInformationDetailsResponse

interface GetAppInformationDetailsUseCase : BaseUseCase<Int, AppInformationDetailsResponse> {

    override suspend operator fun invoke(id: Int)
            : Results<AppInformationDetailsResponse>

}