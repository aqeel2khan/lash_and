package com.cosmetics.domain.interaction.appInfo

import com.cosmetics.domain.base.BaseUseCase
import com.cosmetics.domain.model.Results
import com.cosmetics.domain.model.home.appinfo.AppInformationResponse

interface GetAppInformationUseCase : BaseUseCase<String, AppInformationResponse> {

    override suspend operator fun invoke(emptyString: String)
            : Results<AppInformationResponse>

}