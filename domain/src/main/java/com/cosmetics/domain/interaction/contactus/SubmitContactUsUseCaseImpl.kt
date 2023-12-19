package com.cosmetics.domain.interaction.contactus

import com.cosmetics.domain.base.BaseResponse
import com.cosmetics.domain.model.Results
import com.cosmetics.domain.model.home.contactus.ContactRequest
import com.cosmetics.domain.repository.ContactUsRepository

class SubmitContactUsUseCaseImpl(private val contactUsRepository: ContactUsRepository) :
    SubmitContactUsUseCase {
    override suspend fun invoke(contactRequest: ContactRequest): Results<BaseResponse> =
        contactUsRepository.submitNote(contactRequest)
}