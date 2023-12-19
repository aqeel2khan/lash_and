package com.cosmetics.domain.interaction.appInfo

import com.cosmetics.domain.model.Results
import com.cosmetics.domain.model.home.appinfo.AppInformationResponse
import com.cosmetics.domain.repository.AppInfoRepository

class GetAppInformationUseCaseImpl(private val appInfoRepository: AppInfoRepository) :
    GetAppInformationUseCase {
    override suspend fun invoke(emptyString: String): Results<AppInformationResponse> =
        appInfoRepository.getInformationList()
}