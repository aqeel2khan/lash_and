package com.cosmetics.domain.interaction.appInfo

import com.cosmetics.domain.model.Results
import com.cosmetics.domain.model.home.appinfo.AppInformationDetailsResponse
import com.cosmetics.domain.repository.AppInfoRepository

class GetAppInformationDetailsUseCaseImpl(private val appInfoRepository: AppInfoRepository) :
    GetAppInformationDetailsUseCase {
    override suspend fun invoke(id: Int): Results<AppInformationDetailsResponse> =
        appInfoRepository.getInformationDetails(id)
}