package com.cosmetics.domain.interaction.contactus

import com.cosmetics.domain.base.BaseResponse
import com.cosmetics.domain.base.BaseUseCase
import com.cosmetics.domain.model.Results
import com.cosmetics.domain.model.home.contactus.ContactRequest

interface SubmitContactUsUseCase : BaseUseCase<ContactRequest, BaseResponse> {

    override suspend operator fun invoke(contactRequest: ContactRequest): Results<BaseResponse>

}