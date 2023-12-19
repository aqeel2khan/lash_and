package com.cosmetics.domain.repository

import com.cosmetics.domain.model.Results
import com.cosmetics.domain.model.home.appinfo.AppInformationDetailsResponse
import com.cosmetics.domain.model.home.appinfo.AppInformationResponse

interface AppInfoRepository {
    suspend fun getInformationList(): Results<AppInformationResponse>
    suspend fun getInformationDetails(id: Int): Results<AppInformationDetailsResponse>
}
