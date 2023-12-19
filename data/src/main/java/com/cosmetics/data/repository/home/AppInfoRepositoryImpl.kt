package com.cosmetics.data.repository.home

import com.cosmetics.data.networking.service.AppInformationService
import com.cosmetics.data.utils.safeApiCall
import com.cosmetics.domain.model.Results
import com.cosmetics.domain.model.home.appinfo.AppInformationDetailsResponse
import com.cosmetics.domain.model.home.appinfo.AppInformationResponse
import com.cosmetics.domain.repository.AppInfoRepository

class AppInfoRepositoryImpl(private val appInformationService: AppInformationService) :
    AppInfoRepository {
    override suspend fun getInformationList(): Results<AppInformationResponse> =
        safeApiCall {
            appInformationService.getInformationList()
        }

    override suspend fun getInformationDetails(id: Int): Results<AppInformationDetailsResponse> =
        safeApiCall {
            appInformationService.getInformationDetails(id)
        }
}