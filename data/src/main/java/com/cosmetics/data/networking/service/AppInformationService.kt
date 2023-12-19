package com.cosmetics.data.networking.service

import com.cosmetics.domain.model.home.appinfo.AppInformationDetailsResponse
import com.cosmetics.domain.model.home.appinfo.AppInformationResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface AppInformationService {
    @GET("index.php?route=feed/rest_api/information")
    suspend fun getInformationList(): Response<AppInformationResponse>

    @GET("index.php?route=feed/rest_api/information")
    suspend fun getInformationDetails(@Query("id") id: Int): Response<AppInformationDetailsResponse>
}

