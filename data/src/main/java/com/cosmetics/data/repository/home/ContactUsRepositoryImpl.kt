package com.cosmetics.data.repository.home

import com.cosmetics.data.networking.service.HomeService
import com.cosmetics.data.utils.safeApiCall
import com.cosmetics.domain.base.BaseResponse
import com.cosmetics.domain.model.Results
import com.cosmetics.domain.model.home.contactus.ContactRequest
import com.cosmetics.domain.repository.ContactUsRepository

class ContactUsRepositoryImpl(private val homeService: HomeService) : ContactUsRepository {

    override suspend fun submitNote(contactRequest: ContactRequest): Results<BaseResponse> =
        safeApiCall { homeService.submitContactUs(contactRequest) }
}