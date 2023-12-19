package com.cosmetics.domain.repository

import com.cosmetics.domain.base.BaseResponse
import com.cosmetics.domain.model.Results
import com.cosmetics.domain.model.home.contactus.ContactRequest

interface ContactUsRepository {
    suspend fun submitNote(contactRequest: ContactRequest): Results<BaseResponse>
}